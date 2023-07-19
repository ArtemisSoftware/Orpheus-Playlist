package com.artemissoftware.orpheusplaylist.domain.usecases

import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import javax.inject.Inject

class GetUserPlaylistsUseCase @Inject constructor(private val userPlaylistDataStoreRepository: UserPlaylistDataStoreRepository) {

    operator fun invoke() = userPlaylistDataStoreRepository.getPlaylists()
}
