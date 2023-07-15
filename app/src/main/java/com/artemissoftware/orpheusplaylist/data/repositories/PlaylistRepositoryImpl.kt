package com.artemissoftware.orpheusplaylist.data.repositories

import com.artemissoftware.orpheusplaylist.data.models.Album
import com.artemissoftware.orpheusplaylist.data.resolvers.AlbumContentResolver
import com.artemissoftware.orpheusplaylist.data.resolvers.AudioContentResolver
import com.artemissoftware.orpheusplaylist.domain.repositories.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val audioContentResolver: AudioContentResolver,
    private val albumContentResolver: AlbumContentResolver,
) : PlaylistRepository {

    override suspend fun getAlbum(albumId: Long): Album? = withContext(Dispatchers.IO) {
        val albumMetadata = albumContentResolver.getAlbum(albumId = albumId)

        val album = albumMetadata?.let {
            val tracks = audioContentResolver.getTracksFromAlbum(albumId = albumId)
            Album(albumMetadata = it, tracks = tracks)
        } ?: run {
            null
        }

        album
    }
}
