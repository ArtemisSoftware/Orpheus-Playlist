package com.artemissoftware.orpheusplaylist.data.repositories

import com.artemissoftware.orpheusplaylist.data.mappers.toAlbum
import com.artemissoftware.orpheusplaylist.data.mappers.toAlbumStandCover
import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.models.AlbumType
import com.artemissoftware.orpheusplaylist.data.resolvers.AlbumContentResolver
import com.artemissoftware.orpheusplaylist.data.resolvers.AudioContentResolver
import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.domain.models.AlbumStandCover
import com.artemissoftware.orpheusplaylist.domain.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val albumContentResolver: AlbumContentResolver,
    private val audioContentResolver: AudioContentResolver,
) : AlbumRepository {

    override suspend fun getAlbums(): List<AlbumStandCover> = withContext(Dispatchers.IO) {
        albumContentResolver.getAlbums().map { it.toAlbumStandCover(AlbumType.ALBUM) }
    }

    override suspend fun getUserPlaylistAlbum(playlistName: String): AlbumStandCover = withContext(Dispatchers.IO) {
        AlbumMetadata.getUserPlaylistAlbum(playlistName = playlistName).toAlbumStandCover(AlbumType.USER_PLAYLIST_ALBUM)
    }

    override suspend fun getAlbum(albumId: Long, favoriteAudios: List<Long>): Album? = withContext(Dispatchers.IO) {
        val albumMetadata = albumContentResolver.getAlbum(albumId = albumId)
        val tracks = audioContentResolver.getTracksFromAlbum(albumId = albumId, userSelectedAudioIds = favoriteAudios)

        albumMetadata?.toAlbum(tracks = tracks)
    }

    override suspend fun getAlbum(playlistName: String, audioIds: List<Long>): Album = withContext(Dispatchers.IO) {
        val tracks = audioContentResolver.getTracks(audioIds = audioIds)
        val albumMetadata = AlbumMetadata.getUserPlaylistAlbum(playlistName = playlistName)

        albumMetadata.toAlbum(tracks = tracks, type = AlbumType.USER_PLAYLIST_ALBUM)
    }
}
