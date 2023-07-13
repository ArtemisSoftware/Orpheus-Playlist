package com.artemissoftware.orpheusplaylist.headphone.presentation.activity

import com.artemissoftware.orpheusplaylist.headphone.data.model.AudioMetadata

data class AudioPlayerState(
    val isLoading: Boolean = false,
    val audios: List<AudioMetadata> = emptyList(),
    val isPlaying: Boolean = false,
    val selectedAudio: AudioMetadata = AudioMetadata(),
    val currentPosition: Int = 0,
    val likedSongs: List<Long> = emptyList()
)