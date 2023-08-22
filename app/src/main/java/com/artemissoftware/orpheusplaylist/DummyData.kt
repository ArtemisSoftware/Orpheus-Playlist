package com.artemissoftware.orpheusplaylist

import com.artemissoftware.orpheusplaylist.data.mappers.toAlbumStandCover
import com.artemissoftware.orpheusplaylist.data.mappers.toAudio
import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.models.AlbumType
import com.artemissoftware.orpheusplaylist.data.models.ArtistMetadata
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.data.models.TrackPositionMetadata
import com.artemissoftware.orpheusplaylist.domain.models.Album

object DummyData {

    val artistMetadata = ArtistMetadata(name = "The artist")

    val albumMetadata = AlbumMetadata(
        id = 1L,
        name = "The Album",
        artist = artistMetadata,
    )

    val listAlbumMetadata = listOf(albumMetadata, albumMetadata, albumMetadata, albumMetadata, albumMetadata, albumMetadata, albumMetadata, albumMetadata, albumMetadata)
    val listAlbumCovers = listAlbumMetadata.map { it.toAlbumStandCover(AlbumType.ALBUM) }

    val audioPositionMetadata = TrackPositionMetadata(track = 1, disc = 10)

    val audioMetadata = AudioMetadata(
        id = 10L,
        name = "The artist",
        duration = 100L,
        isOnPlaylist = false,
        position = audioPositionMetadata,
        albumMetadata = albumMetadata,
    )

    val audio = audioMetadata.toAudio()

    val listAudioMetadata = listOf(audioMetadata, audioMetadata, audioMetadata)

    val listAudio = listAudioMetadata.map { it.toAudio() }

    val album = Album(
        id = 10L,
        name = "The Album",
        artist = "The artist",
        tracks = listOf(audioMetadata, audioMetadata, audioMetadata).map { it.toAudio() },
    )

    val albumNoTracks = Album(
        id = 10L,
        name = "The Album",
        artist = "The artist",
        tracks = listOf(),
    )
}
