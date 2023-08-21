package com.artemissoftware.orpheusplaylist.domain.models

import android.graphics.Bitmap

data class AlbumStandCover(
    val id: Long,
    val name: String,
    val uri: Bitmap? = null,
    val artist: String,
)
