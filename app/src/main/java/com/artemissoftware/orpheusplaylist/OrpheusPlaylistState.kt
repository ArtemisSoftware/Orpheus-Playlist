package com.artemissoftware.orpheusplaylist

import com.artemissoftware.orpheusplaylist.data.models.AlbumType
import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.domain.models.Audio

data class OrpheusPlaylistState(
    val audioList: List<Audio> = emptyList(),
    val currentPlayBackPosition: Long = 0L,
    val currentAudioProgress: Float = 0f,
    val currentAudioIndex: Int = 0,
    val preLoadedAlbum: Album? = null,
    val loadedAlbum: Album? = null,
    val fullPlayer: Boolean = false,
    val favorites: List<Long> = emptyList(),
) {

    fun getPlayerBarCover(currentPlaying: Audio? = null) = if (loadedAlbum?.type == AlbumType.ALBUM) loadedAlbum.uri else currentPlaying?.albumCover

    fun getAlbum() = (preLoadedAlbum ?: loadedAlbum)
}
