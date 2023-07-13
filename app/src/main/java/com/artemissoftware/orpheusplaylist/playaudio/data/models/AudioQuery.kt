package com.artemissoftware.orpheusplaylist.playaudio.data.models

import android.net.Uri
import android.provider.MediaStore

data class AudioQuery(
    val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
    val projection: Array<String>,
    val selectionClause: String?,
    val selectionArg: Array<String>,
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
                // TODO: fazer isto no fim. Depende de mudar o sdk
                //  MediaStore.Audio.Albums.ARTIST_ID,
                MediaStore.Audio.Albums.ARTIST,
            ),
            selectionClause = "${MediaStore.Audio.AudioColumns.IS_MUSIC} = ?",
            selectionArg = arrayOf("1"),
            sortOrder = "${MediaStore.Audio.AudioColumns.DISPLAY_NAME} ASC",
        )
    }
}
