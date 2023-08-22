package com.artemissoftware.orpheusplaylist.data.mappers

import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.models.AlbumType
import com.artemissoftware.orpheusplaylist.domain.models.AlbumStandCover

fun AlbumMetadata.toAlbumStandCover(type: AlbumType): AlbumStandCover {
    return AlbumStandCover(
        id = id,
        name = name,
        uri = uri,
        artist = artist.name,
        type = type
    )
}
