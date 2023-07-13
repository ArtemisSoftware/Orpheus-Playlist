package com.artemissoftware.orpheusplaylist.playaudio.presentation

import com.artemissoftware.orpheusplaylist.playaudio.data.models.Audio

sealed class AudioPlaylistEvents {
    data class PlayAudio(val audio: Audio) : AudioPlaylistEvents()

    object StopPlayBack : AudioPlaylistEvents()
    object Rewind : AudioPlaylistEvents()

    object FastForward : AudioPlaylistEvents()
    object SkipToNext : AudioPlaylistEvents()
    data class SeekTo(val value: Float) : AudioPlaylistEvents()
}
