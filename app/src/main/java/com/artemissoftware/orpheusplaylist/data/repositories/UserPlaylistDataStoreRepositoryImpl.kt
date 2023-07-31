package com.artemissoftware.orpheusplaylist.data.repositories

import android.content.Context
import com.artemissoftware.orpheusplaylist.data.local.models.UserPlaylists
import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import com.artemissoftware.orpheusplaylist.utils.extensions.playlistsStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPlaylistDataStoreRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) : UserPlaylistDataStoreRepository {

    override fun getPlaylists(): Flow<UserPlaylists> {
        return context.playlistsStore.data
    }

    override suspend fun getPlaylistsTracks(playlistName: String): List<Long> {
        val playlists = context.playlistsStore.data.first()
        return playlists.lists[playlistName] ?: emptyList()
    }

    override suspend fun createPlaylist(name: String): Boolean {
        var created = true
        val lists = getPlaylists().first().lists

        if (lists.containsKey(name)) {
            created = false
        } else {
            context.playlistsStore.updateData { playlists ->

                val data = playlists.lists.toMutableMap()
                data[name] = emptyList()

                playlists.copy(
                    lists = data as HashMap<String, List<Long>>,
                )
            }
        }
        return created
    }

    override suspend fun updatePlaylist(name: String, audioId: Long) {
        context.playlistsStore.updateData { playlists ->

            val userPlaylists = playlists.lists.toMutableMap()
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
                lists = userPlaylists as HashMap<String, List<Long>>,
            )
        }
    }
}
