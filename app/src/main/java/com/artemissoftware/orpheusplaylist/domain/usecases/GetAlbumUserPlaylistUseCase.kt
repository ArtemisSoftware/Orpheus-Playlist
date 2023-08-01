package com.artemissoftware.orpheusplaylist.domain.usecases

import com.artemissoftware.orpheusplaylist.data.models.Album
import com.artemissoftware.orpheusplaylist.domain.repositories.PlaylistRepository
import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAlbumUserPlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    private val userPlaylistDataStoreRepository: UserPlaylistDataStoreRepository,
) {

    suspend operator fun invoke(playlistName: String): Flow<Album> {
        return userPlaylistDataStoreRepository.getPlaylists().map {
            val audioIds = it.lists[playlistName] ?: emptyList()
            playlistRepository.getUserPlaylist(playlistName = playlistName, audioIds = audioIds)
        }
    }
}
