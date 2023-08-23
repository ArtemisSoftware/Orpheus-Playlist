package com.artemissoftware.orpheusplaylist

import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.domain.models.Audio

sealed class OrpheusPlaylistEvents {
    data class PlayAudio(val track: Audio) : OrpheusPlaylistEvents()
    object PlayTrack : OrpheusPlaylistEvents()
    data class SwipePlayTrack(val track: Audio) : OrpheusPlaylistEvents()

    object SkipToNext : OrpheusPlaylistEvents()
    object SkipToPrevious : OrpheusPlaylistEvents()
    data class SeekTo(val value: Float) : OrpheusPlaylistEvents()

    data class PreLoadAlbum(val album: Album) : OrpheusPlaylistEvents()

    data class TogglePlayerDisplay(val isFullDisplay: Boolean) : OrpheusPlaylistEvents()

    data class UpdateUserPlaylist(val audioId: Long) : OrpheusPlaylistEvents()

    data class UpdateAudioIndex(val track: Audio) : OrpheusPlaylistEvents()
}
