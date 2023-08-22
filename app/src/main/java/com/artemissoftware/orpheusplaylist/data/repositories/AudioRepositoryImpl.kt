package com.artemissoftware.orpheusplaylist.data.repositories

import com.artemissoftware.orpheusplaylist.data.resolvers.AudioContentResolver
import com.artemissoftware.orpheusplaylist.domain.repositories.AudioRepository
import com.artemissoftware.orpheusplaylist.domain.models.Audio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(
    private val audioContentResolver: AudioContentResolver,
): AudioRepository {

    override suspend fun getAudioData(): List<Audio> = withContext(Dispatchers.IO) {
        audioContentResolver.getAudioData()
    }
}
