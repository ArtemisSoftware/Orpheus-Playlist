package com.artemissoftware.orpheusplaylist.domain.repositories

import com.artemissoftware.orpheusplaylist.domain.models.Album

interface PlaylistRepository {



    suspend fun getUserPlaylist(playlistName: String, audioIds: List<Long>): Album
}
