package com.artemissoftware.orpheusplaylist.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.artemissoftware.orpheusplaylist.domain.model.AudioMetadata
import com.artemissoftware.orpheusplaylist.domain.repository.AudioPlayerRepository
import com.artemissoftware.orpheusplaylist.util.audio.MetadataHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioPlayerRepositoryImpl @Inject constructor(
    private val metadataHelper: MetadataHelper
) : AudioPlayerRepository {

    override suspend fun loadCoverBitmap(context: Context, uri: Uri): Bitmap? {
        return withContext(Dispatchers.IO) {
            metadataHelper.getAlbumArt(context = context, uri = uri)
        }
    }

    override suspend fun getAudios(): List<AudioMetadata> {
        return withContext(Dispatchers.IO) {
            metadataHelper.getAudios()
        }
    }
}