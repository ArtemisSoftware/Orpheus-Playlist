package com.artemissoftware.orpheusplaylist.utils.extensions

import android.content.Context
import androidx.datastore.dataStore
import com.artemissoftware.orpheusplaylist.data.local.UserPlaylistsSerializer

val Context.playlistsStore by dataStore(
    fileName = "playlists.json",
    serializer = UserPlaylistsSerializer
)
