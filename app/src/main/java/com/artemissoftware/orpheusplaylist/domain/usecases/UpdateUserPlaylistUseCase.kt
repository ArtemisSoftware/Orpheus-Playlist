package com.artemissoftware.orpheusplaylist.domain.usecases

import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import com.artemissoftware.orpheusplaylist.utils.OrpheusConstants
import javax.inject.Inject

class UpdateUserPlaylistUseCase @Inject constructor(
    private val userPlaylistDataStoreRepository: UserPlaylistDataStoreRepository,
) {
    suspend operator fun invoke(audioId: Long) {
        userPlaylistDataStoreRepository.updatePlaylist(name = OrpheusConstants.USER_PLAYLIST_ALBUM_NAME, audioId = audioId)
    }
}
