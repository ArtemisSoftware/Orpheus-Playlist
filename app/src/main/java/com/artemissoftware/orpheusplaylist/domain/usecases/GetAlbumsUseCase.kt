package com.artemissoftware.orpheusplaylist.domain.usecases

import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.domain.repositories.AlbumRepository
import com.artemissoftware.orpheusplaylist.domain.repositories.PlaylistRepository
import com.artemissoftware.orpheusplaylist.domain.repositories.UserPlaylistDataStoreRepository
import com.artemissoftware.orpheusplaylist.utils.OrpheusConstants
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val playlistRepository: PlaylistRepository,
    private val userPlaylistDataStoreRepository: UserPlaylistDataStoreRepository,
) {

    suspend operator fun invoke(): List<AlbumMetadata> {
        val audioIds = userPlaylistDataStoreRepository.getPlaylistsTracks(playlistName = OrpheusConstants.USER_PLAYLIST_NAME)
        val userAlbum = playlistRepository.getUserPlaylist(playlistName = OrpheusConstants.USER_PLAYLIST_NAME, audioIds = audioIds)
        val result = albumRepository.getAlbums().toMutableList()
        result.add(userAlbum.albumMetadata)
        return result.toList()
    }
}
