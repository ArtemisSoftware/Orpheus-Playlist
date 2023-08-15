package com.artemissoftware.orpheusplaylist.playaudio.data

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.WorkerThread
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.playaudio.data.models.Audio
import com.artemissoftware.orpheusplaylist.playaudio.data.models.AudioQuery
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ContentResolverHelper @Inject constructor(@ApplicationContext val context: Context) {

    private var mCursor: Cursor? = null

    @WorkerThread
    fun getAudioData(audioQuery: AudioQuery = AudioQuery.DEFAULT): List<Audio> {
        return getCursorData(audioQuery)
    }

    private fun getCursorData(audioQuery: AudioQuery): MutableList<Audio> {
        val audioList = mutableListOf<Audio>()

        with(audioQuery) {
            mCursor = context.contentResolver.query(
                uri,
                projection,
                selectionClause,
                selectionArg,
                sortOrder,
            )
        }

        mCursor?.use { cursor ->
            val idColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)
            val artistColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)
            val dataColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
            val titleColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)

            cursor.apply {
                if (count == 0) {
                    Log.e("Cursor", "getCursorData: Cursor is Empty")
                } else {
                    while (cursor.moveToNext()) {
                        val displayName = getString(displayNameColumn)
                        val id = getLong(idColumn)
                        val artist = getString(artistColumn)
                        val data = getString(dataColumn)
                        val duration = getInt(durationColumn)
                        val title = getString(titleColumn)
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id,
                        )

                        val albumCover = getAlbumArt(context = context, uri = uri)

                        audioList += Audio(
                            uri,
                            displayName,
                            id,
                            artist,
                            data,
                            duration,
                            title,
                            albumArt = albumCover,
                        )
                    }
                }
            }
        }

        return audioList
    }

    private fun getAlbumArt(context: Context, uri: Uri): Bitmap {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(context, uri)
        val data = mmr.embeddedPicture
        return if (data != null) {
            BitmapFactory.decodeByteArray(data, 0, data.size)
        } else {
            BitmapFactory.decodeResource(context.resources, R.drawable.musics)
        }
    }
}
