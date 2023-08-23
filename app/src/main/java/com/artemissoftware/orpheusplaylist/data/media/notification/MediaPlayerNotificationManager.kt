package com.artemissoftware.orpheusplaylist.data.media.notification

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.artemissoftware.orpheusplaylist.R
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

/**
 * For user to control music playback in the notification
 */
internal class MediaPlayerNotificationManager(context: Context, sessionToken: MediaSessionCompat.Token, notificationListener: PlayerNotificationManager.NotificationListener) {

    private val notificationManager: PlayerNotificationManager

    init {
        val mediaController = MediaControllerCompat(context, sessionToken)

        val builder = PlayerNotificationManager.Builder(
            context,
            MediaPlayerNotificationConstants.PLAYBACK_NOTIFICATION_ID,
            MediaPlayerNotificationConstants.PLAYBACK_NOTIFICATION_CHANNEL_ID,
        )

        with(builder) {
            setMediaDescriptionAdapter(DescriptionAdapter(mediaController))
            setNotificationListener(notificationListener)
            setChannelNameResourceId(R.string.notification_channel)
            setChannelDescriptionResourceId(R.string.notification_channel_description)
        }

        notificationManager = builder.build()

        with(notificationManager) {
            setMediaSessionToken(sessionToken)
            setSmallIcon(R.drawable.ic_music_note)
            setUseRewindAction(false)
            setUseFastForwardAction(false)
        }
    }

    fun hideNotification() {
        notificationManager.setPlayer(null)
    }

    fun showNotification(player: Player) {
        notificationManager.setPlayer(player)
    }

    /**
     * Helps to set the description or content of the media
     */
    inner class DescriptionAdapter(private val controller: MediaControllerCompat) : PlayerNotificationManager.MediaDescriptionAdapter {

        override fun getCurrentContentTitle(player: Player): CharSequence =
            controller.metadata.description.title.toString()

        override fun createCurrentContentIntent(player: Player): PendingIntent? =
            controller.sessionActivity

        override fun getCurrentContentText(player: Player): CharSequence? =
            controller.metadata.description.subtitle

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback,
        ): Bitmap? {
            return null
        }
    }
}
