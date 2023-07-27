package com.artemissoftware.orpheusplaylist.playaudio.data.media.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.media.MediaBrowserServiceCompat
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.playaudio.data.media.MediaPlayerNotificationManager
import com.artemissoftware.orpheusplaylist.playaudio.data.media.MediaSource
import com.artemissoftware.orpheusplaylist.playaudio.data.media.constants.MediaConstants
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

var aId: Int = 0

@AndroidEntryPoint
class MediaPlayerService : MediaBrowserServiceCompat() {

    @Inject
    lateinit var dataSourceFactory: CacheDataSource.Factory

    @Inject
    lateinit var exoPlayer: ExoPlayer

    @Inject
    lateinit var mediaSource: MediaSource

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    /**
     * Helps to connect to the media player.
     * Universal way to interact with a audio/video player.
     */
    private lateinit var mediaSession: MediaSessionCompat

    private lateinit var mediaSessionConnector: MediaSessionConnector

    /**
     * For user to control music playback in the notification
     */
    private lateinit var mediaPlayerNotificationManager: MediaPlayerNotificationManager
    private var currentPlayingMedia: MediaMetadataCompat? = null
    private val isPlayerInitialized = false
    var isForegroundService: Boolean = false

    /**
     * To initialize the media browser
     */
    override fun onCreate() {
        super.onCreate()

        mediaSession = MediaSessionCompat(this, TAG).apply {
            setSessionActivity(getSessionActivityIntent())
            isActive = true
        }

        // To connect media session to the browser
        sessionToken = mediaSession.sessionToken

        mediaPlayerNotificationManager = MediaPlayerNotificationManager(
            this,
            mediaSession.sessionToken,
            PlayerNotificationListener(),
        )

        serviceScope.launch {
            mediaSource.load()
        }

        mediaSessionConnector = MediaSessionConnector(mediaSession).apply {
            setPlaybackPreparer(AudioMediaPlayBackPreparer())
            setQueueNavigator(MediaQueueNavigator(mediaSession))
            setPlayer(exoPlayer)
        }

        mediaPlayerNotificationManager.showNotification(exoPlayer)

        aId = exoPlayer.audioSessionId
        //sendDataToActivity()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?,
    ): BrowserRoot {
        return BrowserRoot(MediaConstants.MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        when (parentId) {
            MediaConstants.MEDIA_ROOT_ID -> {
                val resultsSent = mediaSource.whenReady { isInitialized ->

                    if (isInitialized) {
                        result.sendResult(mediaSource.asMediaItem())
                    } else {
                        result.sendResult(null)
                    }
                }

                if (!resultsSent) {
                    result.detach()
                }
            }

            else -> Unit
        }
    }

    override fun onCustomAction(action: String, extras: Bundle?, result: Result<Bundle>) {
        super.onCustomAction(action, extras, result)
        when (action) {
            MediaConstants.START_MEDIA_PLAY_ACTION -> {
                mediaPlayerNotificationManager.showNotification(exoPlayer)
            }
            MediaConstants.REFRESH_MEDIA_PLAY_ACTION -> {
                mediaSource.refresh()
                notifyChildrenChanged(MediaConstants.MEDIA_ROOT_ID)
            }
            else -> Unit
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop()
        exoPlayer.clearMediaItems()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        exoPlayer.release()
    }

    /**
     * Creates a session activity pending intent to help launch the UI
     */
    private fun getSessionActivityIntent(): PendingIntent? {
        val sessionActivityIntent = packageManager
            ?.getLaunchIntentForPackage(packageName)
            ?.let { sessionIntent ->
                PendingIntent.getActivity(
                    this,
                    0,
                    sessionIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE,
                )
            }
        return sessionActivityIntent
    }

    private fun preparePlayer(mediaMetadata: List<MediaMetadataCompat>, itemToPlay: MediaMetadataCompat?, playWhenReady: Boolean) {
        val indexToPlay = if (currentPlayingMedia == null) {
            0
        } else {
            mediaMetadata.indexOf(itemToPlay)
        }

        exoPlayer.addListener(PlayerEventListener())
        exoPlayer.setMediaSource(mediaSource.asMediaSource(dataSourceFactory))
        exoPlayer.prepare()
        exoPlayer.seekTo(indexToPlay, 0)
        exoPlayer.playWhenReady = playWhenReady
    }

    inner class PlayerNotificationListener : PlayerNotificationManager.NotificationListener {

        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
            stopForeground(STOP_FOREGROUND_REMOVE)
            isForegroundService = false
            stopSelf()
        }

        override fun onNotificationPosted(notificationId: Int, notification: Notification, ongoing: Boolean) {
            if (ongoing && !isForegroundService) {
                ContextCompat.startForegroundService(
                    applicationContext,
                    Intent(applicationContext, this@MediaPlayerService.javaClass),
                )
                startForeground(notificationId, notification)
                isForegroundService = true
            }
        }
    }

    inner class AudioMediaPlayBackPreparer : MediaSessionConnector.PlaybackPreparer {
        override fun onCommand(player: Player, command: String, extras: Bundle?, cb: ResultReceiver?): Boolean {
            return false
        }

        override fun getSupportedPrepareActions(): Long =
            PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
                PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID

        override fun onPrepare(playWhenReady: Boolean) = Unit

        override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle?) {
            mediaSource.whenReady {
                val itemToPlay = mediaSource.audioMediaMetaData.find {
                    it.description.mediaId == mediaId
                }

                currentPlayingMedia = itemToPlay

                preparePlayer(
                    mediaMetadata = mediaSource.audioMediaMetaData,
                    itemToPlay = itemToPlay,
                    playWhenReady = playWhenReady,
                )
            }
        }

        override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle?) = Unit

        override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle?) = Unit
    }

    /**
     * To help navigate between different playback items
     */
    inner class MediaQueueNavigator(mediaSessionCompat: MediaSessionCompat) : TimelineQueueNavigator(mediaSessionCompat) {
        override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat {
            if (windowIndex < mediaSource.audioMediaMetaData.size) {
                return mediaSource.audioMediaMetaData[windowIndex].description
            }

            // return empty item
            return MediaDescriptionCompat.Builder().build()
        }
    }

    /**
     * Help manage events of the exoplayer
     */
    private inner class PlayerEventListener : Player.Listener {

        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_BUFFERING, Player.STATE_READY -> {
                    mediaPlayerNotificationManager.showNotification(exoPlayer)
                }
                else -> {
                    mediaPlayerNotificationManager.hideNotification()
                }
            }
        }

        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)
            currentDuration = player.duration
        }

        override fun onPlayerError(error: PlaybackException) {
            var message = R.string.generic_error

            if (error.errorCode == PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND) {
                message = R.string.error_media_not_found
            }

            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TAG = "MediaPlayerService"

        var currentDuration: Long = 0L
            private set
    }

    private fun sendDataToActivity() {
        val sendLevel = Intent()
        sendLevel.action = "GET_SIGNAL_STRENGTH"
        sendLevel.putExtra("LEVEL_DATA", exoPlayer.audioSessionId)
        sendBroadcast(sendLevel)
    }

}
