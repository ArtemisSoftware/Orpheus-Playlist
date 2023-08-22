package com.artemissoftware.orpheusplaylist.data.service

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import com.artemissoftware.orpheusplaylist.data.mappers.toMediaMetadataCompat
import com.artemissoftware.orpheusplaylist.data.repositories.AudioRepositoryImpl
import com.artemissoftware.orpheusplaylist.domain.models.Audio
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import javax.inject.Inject

typealias OnReadyListener = (Boolean) -> Unit

class MediaSource @Inject constructor(
    private val repository: AudioRepositoryImpl,
) {

    private val onReadyListeners: MutableList<OnReadyListener> = mutableListOf()

    private val isReady: Boolean
        get() = state == AudioSourceState.STATE_INITIALIZED

    var audioMediaMetaData: List<MediaMetadataCompat> = emptyList()
    var data: List<Audio> = emptyList()

    private var state: AudioSourceState = AudioSourceState.STATE_CREATED
        set(value) {
            if (value == AudioSourceState.STATE_CREATED || value == AudioSourceState.STATE_ERROR) {
                synchronized(onReadyListeners) {
                    field = value
                    onReadyListeners.forEach { listener: OnReadyListener ->
                        listener.invoke(isReady)
                    }
                }
            } else {
                field = value
            }
        }

    fun whenReady(listener: OnReadyListener): Boolean {
        return if (state == AudioSourceState.STATE_CREATED || state == AudioSourceState.STATE_INITIALIZING) {
            onReadyListeners += listener
            false
        } else {
            listener.invoke(isReady)
            true
        }
    }

    suspend fun load() {
        state = AudioSourceState.STATE_INITIALIZING
        data = repository.getAudioData()
        audioMediaMetaData = data.map { it.toMediaMetadataCompat() }
        state = AudioSourceState.STATE_INITIALIZED
    }

    fun asMediaSource(dataSource: CacheDataSource.Factory): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()

        audioMediaMetaData.forEach { mediaMetadataCompat ->
            val mediaItem = MediaItem.fromUri(
                mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI),
            )

            val mediaSource = ProgressiveMediaSource.Factory(dataSource)
                .createMediaSource(mediaItem)

            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    fun asMediaItem() = audioMediaMetaData.map { metaData ->
        val description = MediaDescriptionCompat.Builder()
            .setTitle(metaData.description.title)
            .setMediaId(metaData.description.mediaId)
            .setSubtitle(metaData.description.subtitle)
            .setMediaUri(metaData.description.mediaUri)
            .build()
        MediaBrowserCompat.MediaItem(description, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
    }.toMutableList()

    fun refresh(playlistIds: List<Long>) {
        onReadyListeners.clear()
        audioMediaMetaData = data.filter { playlistIds.contains(it.id) }.map { it.toMediaMetadataCompat() }
    }
}
