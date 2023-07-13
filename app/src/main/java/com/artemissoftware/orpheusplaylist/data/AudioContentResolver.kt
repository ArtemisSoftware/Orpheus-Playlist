package com.artemissoftware.orpheusplaylist.data

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.models.ArtistMetadata
import com.artemissoftware.orpheusplaylist.playaudio.data.models.Audio
import com.artemissoftware.orpheusplaylist.playaudio.data.models.AudioQuery
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AudioContentResolver @Inject constructor(@ApplicationContext val context: Context) {

    @WorkerThread
    fun getAlbums(): List<AlbumMetadata> {
        return getCursorData()
    }

    private fun getCursorData(): MutableList<AlbumMetadata> {
        val albums: MutableList<AlbumMetadata> = mutableListOf()

        val queryCursor = with(AudioQuery.ALBUMS) {
            context.contentResolver.query(
                uri,
                projection,
                selectionClause,
                selectionArg,
                sortOrder,
            )
        }

        queryCursor?.use { cursor ->

            val idColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums._ID)
            val albumNameColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)

            // TODO: fazer isto no fim. Depende de mudar o sdk
            //  val artistIdColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID)

            val artistNameColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)

            cursor.apply {
                if (count == 0) {
                    Log.e("Cursor", "getCursorData: Cursor is Empty")
                } else {
                    while (cursor.moveToNext()) {
                        val id: Long = cursor.getLongOrNull(idColumn) ?: continue

                        // TODO: fazer isto no fim. Depende de mudar o sdk
                        //  val artistId: Long = cursor.getLongOrNull(artistIdIndex) ?: continue
                        val artistName: String = cursor.getStringOrNull(artistNameColumn) ?: continue
                        val albumName: String = cursor.getStringOrNull(albumNameColumn) ?: continue



                        albums += AlbumMetadata(
                            id = id,
                            name = albumName,
                            artist = ArtistMetadata(
                                name = artistName,
                            ),
                        )
                    }
                }
            }
        }

        return albums
    }
}
