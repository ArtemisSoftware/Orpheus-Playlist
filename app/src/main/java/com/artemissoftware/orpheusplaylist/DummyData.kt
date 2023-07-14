package com.artemissoftware.orpheusplaylist

import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.models.ArtistMetadata

object DummyData {

    val artistMetadata = ArtistMetadata(name = "The artist")

    val albumMetadata = AlbumMetadata(
        id = 1L,
        name = "The Album",
        artist = artistMetadata,
    )

    val listAlbumMetadata = listOf(albumMetadata, albumMetadata, albumMetadata)
}
