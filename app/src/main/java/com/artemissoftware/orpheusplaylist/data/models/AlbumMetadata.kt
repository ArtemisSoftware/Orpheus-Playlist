package com.artemissoftware.orpheusplaylist.data.models

data class AlbumMetadata(
    val id: Long,
    val name: String,
    val artist: ArtistMetadata,
)
