package com.artemissoftware.orpheusplaylist.domain.model

import android.graphics.Bitmap
import android.net.Uri

data class AudioMetadata(
    val contentUri: Uri = Uri.EMPTY,
    val songId: Long = 0L,
    val cover: Bitmap? = null,
    val songTitle: String = "",
    val artist: String = "",
    val duration: Int = 0
)
