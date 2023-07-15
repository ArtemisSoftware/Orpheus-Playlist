package com.artemissoftware.orpheusplaylist.data.models

data class Album(
    val albumMetadata: AlbumMetadata,
    val tracks: List<AudioMetadata>
)
