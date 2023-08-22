package com.artemissoftware.orpheusplaylist.presentation.albums

import com.artemissoftware.orpheusplaylist.domain.models.AlbumStandCover

data class AlbumState(
    val albums: List<AlbumStandCover> = emptyList(),
)
