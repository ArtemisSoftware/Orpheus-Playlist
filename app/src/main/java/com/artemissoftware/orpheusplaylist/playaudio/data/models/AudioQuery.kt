package com.artemissoftware.orpheusplaylist.playaudio.data.models

import android.net.Uri
import android.provider.MediaStore

data class AudioQuery(
    val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
    val projection: Array<String>,
    val selectionClause: String? = null,
    val selectionArg: Array<String>? = null,
    val sortOrder: String,
) {

    companion object {

        val DEFAULT = AudioQuery(
            projection = arrayOf(
                MediaStore.Audio.AudioColumns.DISPLAY_NAME,
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.AudioColumns.TITLE,
            ),
            selectionClause = "${MediaStore.Audio.AudioColumns.IS_MUSIC} = ?",
            selectionArg = arrayOf("1"),
            sortOrder = "${MediaStore.Audio.AudioColumns.DISPLAY_NAME} ASC",
        )

        val ALBUMS = AudioQuery(
            projection = arrayOf(
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                // TODO: fazer isto no fim. Depende de mudar o sdk
                //  MediaStore.Audio.Albums.ARTIST_ID,
                MediaStore.Audio.Albums.ARTIST,
            ),
            sortOrder = "${MediaStore.Audio.Albums.ALBUM} ASC",
        )

        fun getAlbumQuery(albumId: Long): AudioQuery {
            return AudioQuery(
                projection = arrayOf(
                    MediaStore.Audio.Albums._ID,
                    MediaStore.Audio.Albums.ALBUM,
                    MediaStore.Audio.Media.ALBUM_ID,
                    // TODO: fazer isto no fim. Depende de mudar o sdk
                    //  MediaStore.Audio.Albums.ARTIST_ID,
                    MediaStore.Audio.Albums.ARTIST,
                ),
                selectionClause = "${MediaStore.Audio.Media.ALBUM_ID} = ?",
                selectionArg = arrayOf(albumId.toString()),
                sortOrder = "${MediaStore.Audio.Albums.ALBUM} ASC",
            )
        }

        fun getQueryTracksFromAlbum(albumId: Long): AudioQuery {
            return AudioQuery(
                projection = arrayOf(
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.CD_TRACK_NUMBER,
                    MediaStore.Audio.Media.DISC_NUMBER,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.RELATIVE_PATH,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.ARTIST_ID,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.ALBUM_ARTIST,
                ),
                selectionClause = "${MediaStore.Audio.Media.ALBUM_ID} = ?",
                selectionArg = arrayOf(albumId.toString()),
                sortOrder = "${MediaStore.Audio.AudioColumns.CD_TRACK_NUMBER} ASC",
            )
        }

//        fun getQueryTracksFromAlbum(albumId: Long): AudioQuery {
//            return AudioQuery(
//                projection = arrayOf(
//                    MediaStore.Audio.Media._ID,
//                    MediaStore.Audio.Media.TITLE,
//                    MediaStore.Audio.Media.CD_TRACK_NUMBER,
//                    MediaStore.Audio.Media.DISC_NUMBER,
//                    MediaStore.Audio.Media.DURATION,
//                    MediaStore.Audio.Media.RELATIVE_PATH,
//                    MediaStore.Audio.Media.ARTIST,
//                    MediaStore.Audio.Media.ARTIST_ID,
//                    MediaStore.Audio.Media.ALBUM_ID,
//                    MediaStore.Audio.Media.ALBUM,
//                    MediaStore.Audio.Media.ALBUM_ARTIST,
//                ),
//                selectionClause = "${MediaStore.Audio.Media.ALBUM_ID} = ?",
//                selectionArg = arrayOf(albumId.toString()),
//                sortOrder = "${MediaStore.Audio.AudioColumns.CD_TRACK_NUMBER} ASC",
//            )
//        }
    }
}
