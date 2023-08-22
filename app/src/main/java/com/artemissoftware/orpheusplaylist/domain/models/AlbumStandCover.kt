package com.artemissoftware.orpheusplaylist.domain.models

import android.graphics.Bitmap
import com.artemissoftware.orpheusplaylist.data.models.AlbumType

data class AlbumStandCover(
    val id: Long,
    val name: String,
    val uri: Bitmap? = null,
    val artist: String,
    val type: AlbumType,
)
