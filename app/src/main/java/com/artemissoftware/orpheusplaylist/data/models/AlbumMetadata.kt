package com.artemissoftware.orpheusplaylist.data.models

import android.graphics.Bitmap

data class AlbumMetadata(
    val id: Long,
    val name: String,
    val uri: Bitmap? = null,
    val artist: ArtistMetadata,
)
