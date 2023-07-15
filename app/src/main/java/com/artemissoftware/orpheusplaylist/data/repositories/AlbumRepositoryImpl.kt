package com.artemissoftware.orpheusplaylist.data.repositories

import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.resolvers.AlbumContentResolver
import com.artemissoftware.orpheusplaylist.domain.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(private val albumContentResolver: AlbumContentResolver) : AlbumRepository {

    override suspend fun getAlbums(): List<AlbumMetadata> = withContext(Dispatchers.IO) {
        albumContentResolver.getAlbums()
    }
}
