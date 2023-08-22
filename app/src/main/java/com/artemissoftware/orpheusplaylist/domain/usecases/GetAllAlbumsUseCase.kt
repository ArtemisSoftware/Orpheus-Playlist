package com.artemissoftware.orpheusplaylist.domain.usecases

import com.artemissoftware.orpheusplaylist.domain.models.AlbumStandCover
import com.artemissoftware.orpheusplaylist.domain.repositories.AlbumRepository
import com.artemissoftware.orpheusplaylist.utils.OrpheusConstants
import javax.inject.Inject

class GetAllAlbumsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository,
) {

    suspend operator fun invoke(): List<AlbumStandCover> = with(albumRepository){
        val userAlbum = getUserPlaylistAlbum(playlistName = OrpheusConstants.USER_PLAYLIST_ALBUM_NAME)
        val result = getAlbums().toMutableList()
        result.add(userAlbum)
        return result.toList()
    }
}
