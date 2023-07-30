package com.artemissoftware.orpheusplaylist.data.models

data class Album(
    val type: AlbumType = AlbumType.ALBUM,
    val albumMetadata: AlbumMetadata,
    val tracks: List<AudioMetadata>,
)
