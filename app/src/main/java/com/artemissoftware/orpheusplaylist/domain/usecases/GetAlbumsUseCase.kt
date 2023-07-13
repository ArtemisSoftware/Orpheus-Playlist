package com.artemissoftware.orpheusplaylist.domain.usecases

import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.domain.repositories.AlbumRepository
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(private val albumRepository: AlbumRepository) {

    suspend operator fun invoke(): List<AlbumMetadata> {
        return albumRepository.getAlbums()
    }
}
