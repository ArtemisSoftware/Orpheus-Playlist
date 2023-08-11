package com.artemissoftware.orpheusplaylist.presentation.userplaylist

sealed class UserPlayListEvents {
    data class UpdateUserPlaylist(val audioId: Long) : UserPlayListEvents()
}
