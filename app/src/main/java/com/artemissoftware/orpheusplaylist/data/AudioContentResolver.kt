package com.artemissoftware.orpheusplaylist.data

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.models.ArtistMetadata
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
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id,
                        )
                        // content://media/external/audio/media/1000000965
                        val albumCover = getAlbumArt(context = context, uri = uri)
                        albums += AlbumMetadata(
                            id = id,
                            name = albumName,
                            uri = albumCover,
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

    @WorkerThread
    fun getAlbumArt(context: Context, uri: Uri): Bitmap? {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(context, uri)

        val bitmap: Bitmap? = try {
            val data = mediaMetadataRetriever.embeddedPicture
            if (data != null) {
                BitmapFactory.decodeByteArray(data, 0, data.size)
            } else {
                null
            }
        } catch (exp: Exception) {
            null
        } finally {
            mediaMetadataRetriever.release()
        }
        return bitmap
    }
}
