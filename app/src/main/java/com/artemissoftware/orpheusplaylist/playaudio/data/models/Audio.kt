package com.artemissoftware.orpheusplaylist.playaudio.data.models

import android.net.Uri

data class Audio(
    val uri: Uri,
    val displayName: String,
    val id: Long,
    val artist: String,
    val data: String,
    val duration: Int,
    val title: String,
){
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
