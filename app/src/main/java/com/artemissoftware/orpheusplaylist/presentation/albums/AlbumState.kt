package com.artemissoftware.orpheusplaylist.presentation.albums

import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata

data class AlbumState(
    val albums: List<AlbumMetadata> = emptyList(),
)
