package com.artemissoftware.orpheusplaylist.data.service

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.compose.runtime.mutableStateOf
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.data.service.MediaPlayerService.Companion.PLAYLIST_IDS
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MediaPlayerServiceConnection @Inject constructor(@ApplicationContext context: Context) {

    private var audioList = listOf<AudioMetadata>()

    private val _isConnected: MutableStateFlow<MediaResource<Boolean>> = MutableStateFlow(MediaResource.Idle())
    val isConnected: StateFlow<MediaResource<Boolean>>
        get() = _isConnected

    private val _playBackState: MutableStateFlow<PlaybackStateCompat?> = MutableStateFlow(null)
    val playBackState: StateFlow<PlaybackStateCompat?>
        get() = _playBackState

    val currentPlayingAudio = mutableStateOf<AudioMetadata?>(null)

    lateinit var mediaControllerCompat: MediaControllerCompat
    private val mediaBrowserServiceCallback = MediaBrowserConnectionCallBack(context)
    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(context, MediaPlayerService::class.java),
        mediaBrowserServiceCallback,
        null,
    ).apply {
        connect()
    }

    val rootMediaId: String
        get() = mediaBrowser.root

    val transportControl: MediaControllerCompat.TransportControls
        get() = mediaControllerCompat.transportControls

    fun subscribe(parentId: String, callBack: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callBack)
    }

    fun unSubscribe(callBack: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(MediaPlayerServiceConstants.MEDIA_ROOT_ID, callBack)
    }

    fun playAudio(audios: List<AudioMetadata>) {
        audioList = audios
        mediaBrowser.sendCustomAction(MediaPlayerServiceConstants.START_MEDIA_PLAY_ACTION, null, null)
    }

    fun skipToNext() {
        transportControl.skipToNext()
    }

    fun skipToPrevious() {
        transportControl.skipToPrevious()
    }

    fun refreshMediaBrowserChildren(audios: List<AudioMetadata>) {
        val bundle = Bundle().apply {
            putLongArray(PLAYLIST_IDS, audios.map { it.id }.toLongArray())
        }

        mediaBrowser.sendCustomAction(
            MediaPlayerServiceConstants.REFRESH_MEDIA_PLAY_ACTION,
            bundle,
            null,
        )
    }

    /**
     * To help deal with the call backs from the browser
     */
    private inner class MediaBrowserConnectionCallBack(private val context: Context) : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            _isConnected.value = MediaResource.Success()
            mediaControllerCompat = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallBack())
            }
        }

        override fun onConnectionSuspended() {
            _isConnected.value = MediaResource.Error(
                "The connection was suspended",
                false,
            )
        }

        override fun onConnectionFailed() {
            _isConnected.value = MediaResource.Error(
                "Couldn't connect to media browser",
                false,
            )
        }
    }

    /**
     * To help deal with the call backs from the controller
     */
    private inner class MediaControllerCallBack : MediaControllerCompat.Callback() {

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            _playBackState.value = state
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
            currentPlayingAudio.value = metadata?.let { data ->
                audioList.find {
                    it.id.toString() == data.description.mediaId
                }
            }
        }

        override fun onSessionDestroyed() {
            super.onSessionDestroyed()
            mediaBrowserServiceCallback.onConnectionSuspended()
        }
    }
}
