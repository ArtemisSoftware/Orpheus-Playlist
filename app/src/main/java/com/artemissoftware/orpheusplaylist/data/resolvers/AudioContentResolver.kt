package com.artemissoftware.orpheusplaylist.data.resolvers

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.models.ArtistMetadata
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.data.models.TrackPositionMetadata
import com.artemissoftware.orpheusplaylist.playaudio.data.models.AudioQuery
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AudioContentResolver @Inject constructor(@ApplicationContext context: Context) : MetadataContentResolver(context = context) {

    @WorkerThread
    fun getTracksFromAlbum(albumId: Long): List<AudioMetadata> {
        return getTracksFromAlbumCursorData(albumId = albumId)
    }

    private fun getTracksFromAlbumCursorData(albumId: Long): MutableList<AudioMetadata> {
        val tracks: MutableList<AudioMetadata> = mutableListOf()

        getQueryCursor(AudioQuery.getQueryTracksFromAlbum(albumId = albumId))?.use { cursor ->

            val idIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val nameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val durationIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)

            val trackPositionIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.CD_TRACK_NUMBER)
            val discPositionIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.DISC_NUMBER)

            val artistIdIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)
            val artistNameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)

            val albumIdIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            val albumNameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val albumArtistNameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ARTIST)

            while (cursor.moveToNext()) {
                val id: Long = cursor.getLongOrNull(idIndex) ?: continue
                val name: String = cursor.getStringOrNull(nameIndex) ?: continue
                val duration: Long = cursor.getLongOrNull(durationIndex) ?: continue

                val trackPosition: Int? = cursor.getIntOrNull(trackPositionIndex)
                val discPosition: Int =
                    cursor.getIntOrNull(discPositionIndex) ?: TrackPositionMetadata.defaultDisc
                val position = TrackPositionMetadata(
                    track = trackPosition ?: discPosition,
                    disc = discPosition,
                )

                val artistId: Long = cursor.getLongOrNull(artistIdIndex) ?: continue
                val artistName: String = cursor.getStringOrNull(artistNameIndex) ?: continue

                val artist = ArtistMetadata(
                    // TODO: fazer isto no fim. para coincidir com o album
                    //  id = artistId,
                    name = artistName,
                )

                val albumId: Long = cursor.getLongOrNull(albumIdIndex) ?: continue
                val albumName: String = cursor.getStringOrNull(albumNameIndex) ?: continue
                val albumArtistName: String? = cursor.getStringOrNull(albumArtistNameIndex)

                val uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id,
                )

                val albumArtist = ArtistMetadata(
                    name = albumArtistName ?: artistName,
                )

                tracks += AudioMetadata(
                    id = id,
                    name = name,
                    duration = duration,
                    position = position,
                    albumMetadata = AlbumMetadata(
                        id = albumId,
                        name = albumName,
                        artist = albumArtist,
                        uri = getAlbumArt(context = context, uri = uri),
                    ),
                )
            }
        }

        tracks.sortBy { it.position.track }
        return tracks
    }

    @WorkerThread
    fun getTracks(audioIds: List<Long>): List<AudioMetadata> {
        return getTracksCursorData(audioIds = audioIds)
    }

    private fun getTracksCursorData(audioIds: List<Long>): List<AudioMetadata> {
        val tracks: MutableList<AudioMetadata> = mutableListOf()

        getQueryCursor(AudioQuery.getQueryTracks(audioIds = audioIds))?.use { cursor ->

            val idIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val nameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val durationIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)

            val trackPositionIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.CD_TRACK_NUMBER)
            val discPositionIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.DISC_NUMBER)

            val artistIdIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)
            val artistNameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)

            val albumIdIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            val albumNameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val albumArtistNameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ARTIST)

            while (cursor.moveToNext()) {
                val id: Long = cursor.getLongOrNull(idIndex) ?: continue
                val name: String = cursor.getStringOrNull(nameIndex) ?: continue
                val duration: Long = cursor.getLongOrNull(durationIndex) ?: continue

                val trackPosition: Int? = cursor.getIntOrNull(trackPositionIndex)
                val discPosition: Int =
                    cursor.getIntOrNull(discPositionIndex) ?: TrackPositionMetadata.defaultDisc
                val position = TrackPositionMetadata(
                    track = trackPosition ?: discPosition,
                    disc = discPosition,
                )

                val artistId: Long = cursor.getLongOrNull(artistIdIndex) ?: continue
                val artistName: String = cursor.getStringOrNull(artistNameIndex) ?: continue

                val artist = ArtistMetadata(
                    // TODO: fazer isto no fim. para coincidir com o album
                    //  id = artistId,
                    name = artistName,
                )

                val albumId: Long = cursor.getLongOrNull(albumIdIndex) ?: continue
                val albumName: String = cursor.getStringOrNull(albumNameIndex) ?: continue
                val albumArtistName: String? = cursor.getStringOrNull(albumArtistNameIndex)

                val uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id,
                )

                val albumArtist = ArtistMetadata(
                    name = albumArtistName ?: artistName,
                )

                tracks += AudioMetadata(
                    id = id,
                    name = name,
                    duration = duration,
                    position = position,
                    albumMetadata = AlbumMetadata(
                        id = albumId,
                        name = albumName,
                        artist = albumArtist,
                        uri = getAlbumArt(context = context, uri = uri),
                    ),
                )
            }
        }

        return tracks
    }
}
