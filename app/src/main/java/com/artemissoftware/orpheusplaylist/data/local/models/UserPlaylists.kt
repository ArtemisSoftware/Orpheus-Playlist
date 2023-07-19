package com.artemissoftware.orpheusplaylist.data.local.models

import kotlinx.serialization.Serializable

@Serializable
data class UserPlaylists(
    val lists: HashMap<String, List<Long>> = hashMapOf()
)
