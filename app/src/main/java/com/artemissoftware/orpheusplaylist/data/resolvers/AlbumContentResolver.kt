package com.artemissoftware.orpheusplaylist.data.resolvers

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata
import com.artemissoftware.orpheusplaylist.data.models.ArtistMetadata
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlbumContentResolver @Inject constructor(@ApplicationContext context: Context) : MetadataContentResolver(context = context) {

    @WorkerThread
    fun getAlbums(): List<AlbumMetadata> {
        return getAlbumsCursorData()
    }

    private fun getAlbumsCursorData(): MutableList<AlbumMetadata> {
        val albums = LinkedHashMap<Long, AlbumMetadata>()

        getQueryCursor(AudioQuery.ALBUMS)?.use { cursor ->

            val idColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums._ID)
            val idAlbumColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            val albumNameColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)

            // TODO: fazer isto no fim. Depende de mudar o sdk
            //  val artistIdColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID)

            val artistNameColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)

            cursor.apply {
                while (cursor.moveToNext()) {
                    val id: Long = cursor.getLongOrNull(idColumn) ?: continue
                    val idAlbum: Long = cursor.getLongOrNull(idAlbumColumn) ?: continue

                    // TODO: fazer isto no fim. Depende de mudar o sdk
                    //  val artistId: Long = cursor.getLongOrNull(artistIdIndex) ?: continue
                    val artistName: String = cursor.getStringOrNull(artistNameColumn) ?: continue
                    val albumName: String = cursor.getStringOrNull(albumNameColumn) ?: continue
                    val uri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id,
                    )

                    val albumCover = getAlbumArt(context = context, uri = uri)
                    val album = AlbumMetadata(
                        id = idAlbum,
                        name = albumName,
                        uri = albumCover,
                        artist = ArtistMetadata(
                            name = artistName,
                        ),
                    )

                    albums[album.id] = album
                }
            }
        }

        return albums.values.toMutableList()
    }

    @WorkerThread
    fun getAlbum(albumId: Long): AlbumMetadata? {
        return getAlbumCursorData(albumId = albumId)
    }

    private fun getAlbumCursorData(albumId: Long): AlbumMetadata? {
        var album: AlbumMetadata? = null

        getQueryCursor(AudioQuery.getAlbumQuery(albumId = albumId))?.use { cursor ->

            val idColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums._ID)
            val idAlbumColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            val albumNameColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)

            // TODO: fazer isto no fim. Depende de mudar o sdk
            //  val artistIdColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID)

            val artistNameColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)

            cursor.apply {
                while (cursor.moveToNext()) {
                    val id: Long = cursor.getLongOrNull(idColumn) ?: continue
                    val idAlbum: Long = cursor.getLongOrNull(idAlbumColumn) ?: continue

                    // TODO: fazer isto no fim. Depende de mudar o sdk
                    //  val artistId: Long = cursor.getLongOrNull(artistIdIndex) ?: continue
                    val artistName: String = cursor.getStringOrNull(artistNameColumn) ?: continue
                    val albumName: String = cursor.getStringOrNull(albumNameColumn) ?: continue
                    val uri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id,
                    )

                    val albumCover = getAlbumArt(context = context, uri = uri)
                    album = AlbumMetadata(
                        id = idAlbum,
                        name = albumName,
                        uri = albumCover,
                        artist = ArtistMetadata(
                            name = artistName,
                        ),
                    )
                }
            }
        }

        return album
    }
}
