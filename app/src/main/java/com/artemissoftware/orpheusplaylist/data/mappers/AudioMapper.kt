package com.artemissoftware.orpheusplaylist.data.mappers

import android.support.v4.media.MediaMetadataCompat
import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.models.AlbumType
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.domain.models.AlbumStandCover
import com.artemissoftware.orpheusplaylist.domain.models.Audio

fun Audio.toMediaMetadataCompat(): MediaMetadataCompat {
    return MediaMetadataCompat
        .Builder()
        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, this.id.toString())
        .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST, this.artist)
        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, this.uri.toString())
        .putString(MediaMetadataCompat.METADATA_KEY_TITLE, this.title)
        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, this.displayName)
        .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, this.duration.toLong())
        .build()
}

fun AlbumMetadata.toAlbumStandCover(type: AlbumType): AlbumStandCover {
    return AlbumStandCover(
        id = id,
        name = name,
        uri = uri,
        artist = artist.name,
        type = type,
    )
}

fun AlbumMetadata.toAlbum(tracks: List<AudioMetadata>, type: AlbumType? = null): Album {
    return Album(
        id = id,
        name = name,
        uri = uri,
        artist = artist.name,
        tracks = tracks.map { it.toAudio() },
        type = type ?: AlbumType.ALBUM,
    )
}

fun AudioMetadata.toAudio(): Audio {
    return Audio(
        id = id,
        artist = albumMetadata.artist.name,
        duration = duration,
        title = name,
        displayName = albumMetadata.name,
        albumCover = albumMetadata.uri,
        uri = uri,
        position = position.track,
        isOnPlaylist = isOnPlaylist,

    )
}
