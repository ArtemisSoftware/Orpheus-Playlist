package com.artemissoftware.orpheusplaylist.playaudio.presentation

import android.support.v4.media.MediaBrowserCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.orpheusplaylist.playaudio.data.media.MediaPlayerServiceConnection
import com.artemissoftware.orpheusplaylist.playaudio.data.media.constants.MediaConstants
import com.artemissoftware.orpheusplaylist.playaudio.data.media.service.MediaPlayerService
import com.artemissoftware.orpheusplaylist.playaudio.data.models.Audio
import com.artemissoftware.orpheusplaylist.playaudio.data.util.extensions.currentPosition
import com.artemissoftware.orpheusplaylist.playaudio.data.util.extensions.isPlaying
import com.artemissoftware.orpheusplaylist.playaudio.domain.usecases.GetAudioDataListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioPlaylistViewModel @Inject constructor(
    private val getAudioDataListUseCase: GetAudioDataListUseCase,
    serviceConnection: MediaPlayerServiceConnection,
) : ViewModel() {

    private val _state = MutableStateFlow(AudioPlaylistState())
    val state: StateFlow<AudioPlaylistState> = _state.asStateFlow()

    lateinit var rootMediaId: String
    private val playbackState = serviceConnection.plaBackState
    val currentPlayingAudio = serviceConnection.currentPlayingAudio
    var currentPlayBackPosition by mutableStateOf(0L)
    var currentAudioProgress = mutableStateOf(0f)
    private var updatePosition = true
    val isAudioPlaying: Boolean
        get() = playbackState.value?.isPlaying == true
    val currentDuration: Long
        get() = MediaPlayerService.currentDuration

    /**
     * To help get the connection between the media browser and the media service
     */
    private val serviceConnection = serviceConnection.also {
        updatePlayBack()
    }

    /**
     * To subscribe to media browser
     */
    private val subscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
            super.onChildrenLoaded(parentId, children)
        }
    }

    init {
        getAudioDataList()
    }

    fun onTriggerEvent(event: AudioPlaylistEvents) {
        when (event) {
            is AudioPlaylistEvents.PlayAudio -> {
                playAudio(event.audio)
            }

            AudioPlaylistEvents.FastForward -> { fastForward() }
            AudioPlaylistEvents.Rewind -> { rewind() }
            is AudioPlaylistEvents.SeekTo -> { seekTo(event.value)}
            AudioPlaylistEvents.SkipToNext -> { skipToNext() }
            AudioPlaylistEvents.StopPlayBack -> { stopPlayBack() }
        }
    }

    private fun getAudioDataList() {
        viewModelScope.launch {
            val result = getAudioDataListUseCase()

            _state.update {
                it.copy(audioList = result)
            }

            checkMediaPlayerServiceConnection()
        }
    }

    private suspend fun checkMediaPlayerServiceConnection() {
        serviceConnection.isConnected.collect {
            if (it) {
                rootMediaId = serviceConnection.rootMediaId
                serviceConnection.plaBackState.value?.apply {
                    _state.update {
                        it.copy(currentPlayBackPosition = position)
                    }
                }
                serviceConnection.subscribe(rootMediaId, subscriptionCallback)
            }
        }
    }

    private fun playAudio(currentAudio: Audio) {
        serviceConnection.playAudio(_state.value.audioList)
        if (currentAudio.id == currentPlayingAudio.value?.id) {
            if (isAudioPlaying) {
                serviceConnection.transportControl.pause()
            } else {
                serviceConnection.transportControl.play()
            }
        } else {
            serviceConnection.transportControl
                .playFromMediaId(
                    currentAudio.id.toString(),
                    null,
                )
        }
    }

    private fun stopPlayBack() {
        serviceConnection.transportControl.stop()
    }

    private fun fastForward() {
        serviceConnection.fastForward()
    }

    private fun rewind() {
        serviceConnection.rewind()
    }

    private fun skipToNext() {
        serviceConnection.skipToNext()
    }

    private fun seekTo(value: Float) {
        serviceConnection.transportControl.seekTo(
            (currentDuration * value / 100f).toLong(),
        )
    }

    private fun updatePlayBack() {
        viewModelScope.launch {
            val position = playbackState.value?.currentPosition ?: 0

            if (currentPlayBackPosition != position) {
                currentPlayBackPosition = position
            }

            if (currentDuration > 0) {
                currentAudioProgress.value = (currentPlayBackPosition.toFloat() / currentDuration.toFloat() * 100f)
            }

            delay(MediaConstants.PLAYBACK_UPDATE_INTERVAL)
            if (updatePosition) {
                updatePlayBack()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        serviceConnection.unSubscribe(MediaConstants.MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
        updatePosition = false
    }
}
