package com.artemissoftware.orpheusplaylist.data.models

data class AudioMetadata(
    val id: Long,
    val name: String,
    val duration: Long,
    val position: TrackPositionMetadata,
    val albumMetadata: AlbumMetadata,
) {
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
