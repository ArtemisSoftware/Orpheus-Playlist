package com.artemissoftware.orpheusplaylist.presentation.userplaylist

import com.artemissoftware.orpheusplaylist.domain.models.Album

data class UserPlaylistState(
    val album: Album? = null,
)
