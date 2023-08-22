package com.artemissoftware.orpheusplaylist.domain.usecases

import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.domain.repositories.AlbumRepository
import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import com.artemissoftware.orpheusplaylist.utils.OrpheusConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAlbumUseCase @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val userPlaylistDataStoreRepository: UserPlaylistDataStoreRepository,
) {

    suspend operator fun invoke(albumId: Long): Flow<Album?> {
        return userPlaylistDataStoreRepository.getPlaylists().map {
            val favoriteAudios = userPlaylistDataStoreRepository.getPlaylistsTracks(playlistName = OrpheusConstants.USER_PLAYLIST_ALBUM_NAME)
            albumRepository.getAlbum(albumId = albumId, favoriteAudios = favoriteAudios)
        }
    }
}
