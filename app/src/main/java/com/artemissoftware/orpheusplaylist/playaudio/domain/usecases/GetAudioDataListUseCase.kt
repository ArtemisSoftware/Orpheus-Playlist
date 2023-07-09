package com.artemissoftware.orpheusplaylist.playaudio.domain.usecases

import com.artemissoftware.orpheusplaylist.playaudio.data.models.Audio
import com.artemissoftware.orpheusplaylist.playaudio.data.repositories.AudioRepository
import javax.inject.Inject

class GetAudioDataListUseCase @Inject constructor(private val repository: AudioRepository) {

    suspend operator fun invoke(): List<Audio> {
        val result = repository.getAudioData()
        return result
    }
}