package com.artemissoftware.orpheusplaylist.data.mappers

import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.domain.models.AlbumStandCover

fun AlbumMetadata.toAlbumStandCover(): AlbumStandCover {
    return AlbumStandCover(
        id = id,
        name = name,
        uri = uri,
        artist = artist.name,
    )
}
