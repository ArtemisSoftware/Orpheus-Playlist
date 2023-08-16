package com.artemissoftware.orpheusplaylist.playaudio.data.mappers

import android.support.v4.media.MediaMetadataCompat
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.playaudio.data.models.Audio

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

fun AudioMetadata.toMediaMetadataCompat(): MediaMetadataCompat {
    return MediaMetadataCompat
        .Builder()
        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, this.id.toString())
        .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST, this.albumMetadata.artist.name)
        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, this.uri.toString())
        .putString(MediaMetadataCompat.METADATA_KEY_TITLE, this.albumMetadata.name)
        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, this.name)
        .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, this.duration.toLong())
        .build()
}
