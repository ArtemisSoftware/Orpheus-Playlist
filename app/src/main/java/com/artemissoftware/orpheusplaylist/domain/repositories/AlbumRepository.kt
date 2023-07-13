package com.artemissoftware.orpheusplaylist.domain.repositories

import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata

interface AlbumRepository {

    suspend fun getAlbums(): List<AlbumMetadata>
}
