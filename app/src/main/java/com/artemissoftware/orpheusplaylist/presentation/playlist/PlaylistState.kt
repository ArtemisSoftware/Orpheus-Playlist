package com.artemissoftware.orpheusplaylist.presentation.playlist

import android.graphics.Bitmap
import com.artemissoftware.orpheusplaylist.data.models.Album
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata

data class PlaylistState(
    val album: Album? = null,
    val selectedTrack: AudioMetadata? = null,
    val selectedTrackIndex: Int = 0,
    val type: AlbumType = AlbumType.ALBUM,
    val albumCover: Bitmap? = null
)
