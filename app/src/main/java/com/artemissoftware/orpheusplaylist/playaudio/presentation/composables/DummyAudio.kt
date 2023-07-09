package com.artemissoftware.orpheusplaylist.playaudio.presentation.composables

import android.graphics.Bitmap
import androidx.core.net.toUri
import com.artemissoftware.orpheusplaylist.playaudio.data.models.Audio

object DummyAudio {

    val audioList = listOf(
        Audio(
            uri = "".toUri(),
            displayName = "Kotlin Programming",
            id = 0L,
            artist = "Hood",
            data = "",
            duration = 12345,
            title = "Android Programming",
            albumArt = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
        ),
        Audio(
            uri = "".toUri(),
            displayName = "Kotlin Programming",
            id = 0L,
            artist = "Lab",
            data = "",
            duration = 25678,
            title = "Android Programming",
            albumArt = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
        ),
        Audio(
            uri = "".toUri(),
            displayName = "Kotlin Programming",
            id = 0L,
            artist = "Android Lab",
            data = "",
            duration = 8765454,
            title = "Android Programming",
            albumArt = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
        ),
        Audio(
            uri = "".toUri(),
            displayName = "Kotlin Programming",
            id = 0L,
            artist = "Kotlin Lab",
            data = "",
            duration = 23456,
            title = "Android Programming",
            albumArt = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
        ),
        Audio(
            uri = "".toUri(),
            displayName = "Kotlin Programming",
            id = 0L,
            artist = "Hood Lab",
            data = "",
            duration = 65788,
            title = "Android Programming",
            albumArt = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
        ),
        Audio(
            uri = "".toUri(),
            displayName = "Kotlin Programming",
            id = 0L,
            artist = "Hood Lab",
            data = "",
            duration = 234567,
            title = "Android Programming",
            albumArt = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
        ),

    )
}
