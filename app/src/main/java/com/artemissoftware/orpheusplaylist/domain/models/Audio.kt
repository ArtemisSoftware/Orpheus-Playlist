package com.artemissoftware.orpheusplaylist.domain.models

import android.graphics.Bitmap
import android.net.Uri

data class Audio(
    val uri: Uri? = null,
    val displayName: String,
    val id: Long,
    val artist: String,
    val duration: Long,
    val title: String,
    val position: Int,
    val isOnPlaylist: Boolean = false,
    val albumCover: Bitmap? = null,
) {
    fun fileName() = displayName.substringBefore(".")

    fun artistName() = if (artist.contains("<unknown>")) "Unknown Artist" else artist

    fun timeStampToDuration(): String {
        val totalSeconds = Math.floor(duration.toLong() / 1E3).toInt()
        val minutes = totalSeconds / 60
        val remainingSeconds = totalSeconds - (minutes * 60)

        return if (duration.toLong() < 0) {
            "--:--"
        } else {
            "%d:%02d".format(minutes, remainingSeconds)
        }
    }
}
