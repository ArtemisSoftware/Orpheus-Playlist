package com.artemissoftware.orpheusplaylist

import com.artemissoftware.orpheusplaylist.data.mappers.toAlbumStandCover
import com.artemissoftware.orpheusplaylist.data.models.Album
import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.models.ArtistMetadata
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.data.models.TrackPositionMetadata

object DummyData {

    val artistMetadata = ArtistMetadata(name = "The artist")

    val albumMetadata = AlbumMetadata(
        id = 1L,
        name = "The Album",
        artist = artistMetadata,
    )

    val listAlbumMetadata = listOf(albumMetadata, albumMetadata, albumMetadata)
    val listAlbumCovers = listAlbumMetadata.map { it.toAlbumStandCover() }

    val audioPositionMetadata = TrackPositionMetadata(track = 1, disc = 10)

    val audioMetadata = AudioMetadata(
        id = 10L,
        name = "The artist",
        duration = 100L,
        isOnPlaylist = false,
        position = audioPositionMetadata,
        albumMetadata = albumMetadata,
    )

    val listAudioMetadata = listOf(audioMetadata, audioMetadata, audioMetadata)

    val album = Album(albumMetadata = albumMetadata, tracks = listOf(audioMetadata, audioMetadata, audioMetadata))

    val albumNoTracks = Album(albumMetadata = albumMetadata, tracks = listOf())
}
