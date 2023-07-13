package com.artemissoftware.orpheusplaylist.headphone.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.artemissoftware.orpheusplaylist.headphone.data.MetadataHelper
import com.artemissoftware.orpheusplaylist.headphone.data.model.AudioMetadata
import com.artemissoftware.orpheusplaylist.headphone.domain.repository.AudioPlayerRepository
import com.artemissoftware.orpheusplaylist.headphone.util.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioPlayerRepositoryImpl @Inject constructor(
    private val metadataHelper: MetadataHelper,
    private val userPreferences: UserPreferences,
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

    override suspend fun likeOrNotSong(id: Long) {
        withContext(Dispatchers.IO){
            userPreferences.likeOrNotSong(id = id)
        }
    }

    override fun getLikedSongs(): Flow<List<Long>> {
        return userPreferences.likedSongs
    }
}
