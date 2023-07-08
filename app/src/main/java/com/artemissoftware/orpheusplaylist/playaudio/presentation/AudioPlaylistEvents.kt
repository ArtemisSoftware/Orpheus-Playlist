package com.artemissoftware.orpheusplaylist.playaudio.presentation

import com.artemissoftware.orpheusplaylist.playaudio.data.models.Audio

sealed class AudioPlaylistEvents {
    data class PlayAudio(val audio: Audio) : AudioPlaylistEvents()
}
