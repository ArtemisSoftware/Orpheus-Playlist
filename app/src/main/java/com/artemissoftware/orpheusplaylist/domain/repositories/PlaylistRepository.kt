package com.artemissoftware.orpheusplaylist.domain.repositories

import com.artemissoftware.orpheusplaylist.data.models.Album

interface PlaylistRepository {

    suspend fun getAlbum(albumId: Long): Album?

    suspend fun getUserPlaylist(playlistName: String, audioIds: List<Long>): Album
}
