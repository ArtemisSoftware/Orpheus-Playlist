package com.artemissoftware.orpheusplaylist.presentation.playlist

import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata

sealed class PlayListEvents {

    data class SelectTrack(val track: AudioMetadata) : PlayListEvents()

    data class UpdateUserPlaylist(val audioId: Long) : PlayListEvents()

    object SkipToNextTrack : PlayListEvents()
    object SkipToPreviousTrack : PlayListEvents()
    data class RemoveTrackFromPlaylist(val audioId: Long) : PlayListEvents()
}
