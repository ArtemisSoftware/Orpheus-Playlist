package com.artemissoftware.orpheusplaylist.playaudio.data.repositories

import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.data.resolvers.AudioContentResolver
import com.artemissoftware.orpheusplaylist.playaudio.data.ContentResolverHelper
import com.artemissoftware.orpheusplaylist.playaudio.data.models.Audio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioRepository @Inject constructor(
    private val contentResolverHelper: ContentResolverHelper,
    private val audioContentResolver: AudioContentResolver,
) {

    suspend fun getAudioData(): List<Audio> = withContext(Dispatchers.IO) {
        contentResolverHelper.getAudioData()
    }

    suspend fun getAudioData(list: List<Long>): List<AudioMetadata> = withContext(Dispatchers.IO) {
        audioContentResolver.getTracks(list)
    }
}
