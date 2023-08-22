package com.artemissoftware.orpheusplaylist.domain.repositories

import com.artemissoftware.orpheusplaylist.domain.models.Audio

interface AudioRepository {

    suspend fun getAudioData(): List<Audio>
}
