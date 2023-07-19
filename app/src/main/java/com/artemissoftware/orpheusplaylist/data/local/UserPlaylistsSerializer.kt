package com.artemissoftware.orpheusplaylist.data.local

import androidx.datastore.core.Serializer
import com.artemissoftware.orpheusplaylist.data.local.models.UserPlaylists
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserPlaylistsSerializer : Serializer<UserPlaylists> {

    override val defaultValue: UserPlaylists
        get() = UserPlaylists()

    override suspend fun readFrom(input: InputStream): UserPlaylists {
        return try {
            Json.decodeFromString(
                deserializer = UserPlaylists.serializer(),
                string = input.readBytes().decodeToString(),
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserPlaylists, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = UserPlaylists.serializer(),
                value = t,
            ).encodeToByteArray(),
        )
    }
}
