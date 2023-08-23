package com.artemissoftware.orpheusplaylist.data.media.service

import kotlin.time.Duration.Companion.seconds

object MediaPlayerServiceConstants {

    const val START_MEDIA_PLAY_ACTION = "START_MEDIA_PLAY_ACTION"
    const val REFRESH_MEDIA_PLAY_ACTION = "REFRESH_MEDIA_PLAY_ACTION"

    const val MEDIA_ROOT_ID = "media_root_id"
    val PLAYBACK_UPDATE_INTERVAL = 1.seconds
}
