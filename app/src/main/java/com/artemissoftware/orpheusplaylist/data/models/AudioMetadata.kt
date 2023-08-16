package com.artemissoftware.orpheusplaylist.data.models

import android.net.Uri
import kotlin.math.floor

data class AudioMetadata(
    val id: Long,
    val name: String,
    val duration: Long,
    val isOnPlaylist: Boolean = false,
    val position: TrackPositionMetadata,
    val albumMetadata: AlbumMetadata,
    val uri: Uri? = null,
) {
    fun timeStampToDuration(): String {
        val totalSeconds = floor(duration / 1E3).toInt()
        val minutes = totalSeconds / 60
        val remainingSeconds = totalSeconds - (minutes * 60)

        return if (duration < 0) {
            "--:--"
        } else {
            "%d:%02d".format(minutes, remainingSeconds)
        }
    }
}
