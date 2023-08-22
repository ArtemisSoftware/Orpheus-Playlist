package com.artemissoftware.orpheusplaylist.data.resolvers

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.annotation.WorkerThread

abstract class MetadataContentResolver constructor(val context: Context) {

    protected fun getQueryCursor(audioQuery: AudioQuery): Cursor? = with(audioQuery) {
        return context.contentResolver.query(
            uri,
            projection,
            selectionClause,
            selectionArg,
            sortOrder,
        )
    }

    @WorkerThread
    protected fun getAlbumArt(context: Context, uri: Uri): Bitmap? {
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
