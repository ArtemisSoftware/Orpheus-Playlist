package com.artemissoftware.orpheusplaylist.presentation.playlist

sealed class PlayListEvents {

    data class AddTrackToFavorites(val audioId: Long) : PlayListEvents()

    data class RemoveTrackFromFavorites(val audioId: Long) : PlayListEvents()
}
