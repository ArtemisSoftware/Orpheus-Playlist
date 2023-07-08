package com.artemissoftware.orpheusplaylist.playaudio.presentation

import com.artemissoftware.orpheusplaylist.playaudio.data.models.Audio

data class AudioPlaylistState(
    val audioList: List<Audio> = emptyList()
)
