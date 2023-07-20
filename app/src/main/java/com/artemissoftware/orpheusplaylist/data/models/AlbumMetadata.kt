package com.artemissoftware.orpheusplaylist.data.models

import android.graphics.Bitmap

data class AlbumMetadata(
    val id: Long,
    val name: String,
    val uri: Bitmap? = null,
    val artist: ArtistMetadata,
    val tracks: List<AudioMetadata> = emptyList(),
){

    companion object{

        fun getUserPlaylistAlbum(playlistName: String): AlbumMetadata{
            return AlbumMetadata(
                id = 0L,
                name = playlistName,
                artist = ArtistMetadata(
                    name = ""
                )
            )
        }
    }
}
