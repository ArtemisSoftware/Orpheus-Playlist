package com.artemissoftware.orpheusplaylist.domain.usecases

import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import javax.inject.Inject

class CreateUserPlaylistUseCase @Inject constructor(
    private val userPlaylistDataStoreRepository: UserPlaylistDataStoreRepository,
) {

    suspend operator fun invoke(playlistName: String) {
        userPlaylistDataStoreRepository.createPlaylist(name = playlistName)
    }
}
