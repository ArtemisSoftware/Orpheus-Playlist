package com.artemissoftware.orpheusplaylist.domain.repositories

import com.artemissoftware.orpheusplaylist.domain.models.AlbumStandCover

interface AlbumRepository {

    suspend fun getAlbums(): List<AlbumStandCover>

    suspend fun getUserPlaylistAlbum(playlistName: String): AlbumStandCover

}
