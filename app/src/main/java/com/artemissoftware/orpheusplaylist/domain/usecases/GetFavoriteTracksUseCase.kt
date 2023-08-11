package com.artemissoftware.orpheusplaylist.domain.usecases

import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import javax.inject.Inject

class GetFavoriteTracksUseCase @Inject constructor(private val userPlaylistDataStoreRepository: UserPlaylistDataStoreRepository) {
    operator fun invoke(playlistName: String) = userPlaylistDataStoreRepository.getFavorites(playlistName)
}
