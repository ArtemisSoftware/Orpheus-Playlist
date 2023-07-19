package com.artemissoftware.orpheusplaylist.data.repositories

import android.content.Context
import com.artemissoftware.orpheusplaylist.data.local.models.UserPlaylists
import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import com.artemissoftware.orpheusplaylist.utils.extensions.playlistsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserPlaylistDataStoreRepositoryImpl(private val context: Context) : UserPlaylistDataStoreRepository {

    override fun getPlaylists(): Flow<UserPlaylists> {
        return context.playlistsStore.data
    }

    override suspend fun createPlaylist(name: String): Boolean {
        var created = true
        val lists = getPlaylists().first().lists

        if (lists.containsKey(name)) {
            created = false
        } else {
            context.playlistsStore.updateData { playlists ->

                val data = playlists.lists
                data[name] = emptyList()

                playlists.copy(
                    lists = data,
                )
            }
        }
        return created
    }

    override suspend fun updatePlaylist(name: String, audioId: Long) {
        context.playlistsStore.updateData { playlists ->

            val userPlaylists = playlists.lists
            val trackLists = playlists.lists[name]?.toMutableList()

            trackLists?.let {
                if (it.contains(audioId)) {
                    it.remove(audioId)
                } else {
                    it.add(audioId)
                }
            }
            userPlaylists[name] = trackLists ?: emptyList()
            playlists.copy(
                lists = userPlaylists,
            )
        }
    }
}
