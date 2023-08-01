package com.artemissoftware.orpheusplaylist.domain.repositories

import com.artemissoftware.orpheusplaylist.data.models.Album

interface PlaylistRepository {

    suspend fun getAlbum(albumId: Long, userSelectedAudioIds: List<Long> = emptyList()): Album?

    suspend fun getUserPlaylist(playlistName: String, audioIds: List<Long>): Album
}
