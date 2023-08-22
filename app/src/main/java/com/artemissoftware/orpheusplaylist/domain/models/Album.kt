package com.artemissoftware.orpheusplaylist.domain.models

import android.graphics.Bitmap
import com.artemissoftware.orpheusplaylist.data.models.AlbumType

data class Album(
    val type: AlbumType = AlbumType.ALBUM,
    val id: Long,
    val name: String,
    val uri: Bitmap? = null,
    val artist: String,
    val tracks: List<Audio>,
)
