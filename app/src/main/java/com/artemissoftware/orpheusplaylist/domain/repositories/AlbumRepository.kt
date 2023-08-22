package com.artemissoftware.orpheusplaylist.domain.repositories

import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.domain.models.AlbumStandCover

interface AlbumRepository {

    suspend fun getAlbums(): List<AlbumStandCover>

    suspend fun getUserPlaylistAlbum(playlistName: String): AlbumStandCover

    suspend fun getAlbum(albumId: Long, favoriteAudios: List<Long> = emptyList()): Album?

    suspend fun getAlbum(playlistName: String, audioIds: List<Long>): Album
}
