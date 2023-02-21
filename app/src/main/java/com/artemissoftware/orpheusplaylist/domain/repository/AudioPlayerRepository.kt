package com.artemissoftware.orpheusplaylist.domain.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.artemissoftware.orpheusplaylist.domain.model.AudioMetadata
import kotlinx.coroutines.flow.Flow

interface AudioPlayerRepository {

    suspend fun loadCoverBitmap(context: Context, uri: Uri): Bitmap?

    suspend fun getAudios(): List<AudioMetadata>
}