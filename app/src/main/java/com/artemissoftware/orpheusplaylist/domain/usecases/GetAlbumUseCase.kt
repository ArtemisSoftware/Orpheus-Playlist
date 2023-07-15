package com.artemissoftware.orpheusplaylist.domain.usecases

import com.artemissoftware.orpheusplaylist.data.models.Album
import com.artemissoftware.orpheusplaylist.domain.repositories.PlaylistRepository
import javax.inject.Inject

class GetAlbumUseCase @Inject constructor(private val playlistRepository: PlaylistRepository) {

    suspend operator fun invoke(albumId: Long): Album? {
        return playlistRepository.getAlbum(albumId = albumId)
    }
}
