package com.artemissoftware.orpheusplaylist.presentation.activity

import com.artemissoftware.orpheusplaylist.domain.model.AudioMetadata

data class AudioPlayerState(
    val isLoading: Boolean = false,
    val audios: List<AudioMetadata> = emptyList(),
    val isPlaying: Boolean = false,
    val selectedAudio: AudioMetadata = AudioMetadata(),
    val currentPosition: Int = 0,
    val likedSongs: List<Long> = emptyList()
)