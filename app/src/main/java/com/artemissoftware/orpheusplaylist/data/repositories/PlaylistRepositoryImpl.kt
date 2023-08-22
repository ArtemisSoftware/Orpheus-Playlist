package com.artemissoftware.orpheusplaylist.data.repositories

import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.models.AlbumType
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











    override suspend fun getUserPlaylist(playlistName: String, audioIds: List<Long>): Album = withContext(Dispatchers.IO) {
        val tracks = audioContentResolver.getTracks(audioIds = audioIds)

        val album = Album(
            albumMetadata = AlbumMetadata.getUserPlaylistAlbum(playlistName = playlistName),
            tracks = tracks,
            type = AlbumType.USER_PLAYLIST_ALBUM,
        )

        album
    }
}
