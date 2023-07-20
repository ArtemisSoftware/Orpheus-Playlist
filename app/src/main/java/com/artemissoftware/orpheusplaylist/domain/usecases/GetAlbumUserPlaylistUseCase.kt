package com.artemissoftware.orpheusplaylist.domain.usecases

import com.artemissoftware.orpheusplaylist.data.models.Album
import com.artemissoftware.orpheusplaylist.domain.repositories.PlaylistRepository
import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import javax.inject.Inject

class GetAlbumUserPlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    private val userPlaylistDataStoreRepository: UserPlaylistDataStoreRepository,
) {

    suspend operator fun invoke(playlistName: String): Album {
        val audioIds = userPlaylistDataStoreRepository.getPlaylistsTracks(playlistName = playlistName)
        return playlistRepository.getUserPlaylist(playlistName = playlistName, audioIds = audioIds)
    }
}
