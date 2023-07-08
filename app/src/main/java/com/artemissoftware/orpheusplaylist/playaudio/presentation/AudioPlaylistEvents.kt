package com.artemissoftware.orpheusplaylist.playaudio.presentation

sealed class AudioPlaylistEvents {
    data class PlayAudio(val audioId: Long) : AudioPlaylistEvents()
}
