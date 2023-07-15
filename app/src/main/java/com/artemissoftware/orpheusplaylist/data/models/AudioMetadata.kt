package com.artemissoftware.orpheusplaylist.data.models

data class AudioMetadata(
    val id: Long,
    val name: String,
    val duration: Long,
    val position: TrackPositionMetadata,
    val albumMetadata: AlbumMetadata
)
