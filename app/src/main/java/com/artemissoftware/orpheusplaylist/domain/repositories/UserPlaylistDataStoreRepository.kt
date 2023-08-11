package com.artemissoftware.orpheusplaylist.domain.repositories

import com.artemissoftware.orpheusplaylist.data.local.models.UserPlaylists
import kotlinx.coroutines.flow.Flow

interface UserPlaylistDataStoreRepository {

    fun getPlaylists(): Flow<UserPlaylists>

    fun getFavorites(playlistName: String): Flow<List<Long>>

    suspend fun getPlaylistsTracks(playlistName: String): List<Long>

    suspend fun createPlaylist(name: String): Boolean

    suspend fun updatePlaylist(name: String, audioId: Long)
}
