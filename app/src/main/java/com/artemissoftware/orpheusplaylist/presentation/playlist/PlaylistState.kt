package com.artemissoftware.orpheusplaylist.presentation.playlist

import com.artemissoftware.orpheusplaylist.data.models.Album
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata

data class PlaylistState(
    val album: Album? = null,
)
