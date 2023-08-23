package com.artemissoftware.orpheusplaylist

import android.support.v4.media.MediaBrowserCompat
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.orpheusplaylist.data.media.service.MediaPlayerService.Companion.currentDuration
import com.artemissoftware.orpheusplaylist.data.media.service.MediaPlayerServiceConnection
import com.artemissoftware.orpheusplaylist.data.media.service.MediaPlayerServiceConstants
import com.artemissoftware.orpheusplaylist.data.media.service.MediaResource
import com.artemissoftware.orpheusplaylist.data.media.service.audioSessionId
import com.artemissoftware.orpheusplaylist.data.util.extensions.currentPosition
import com.artemissoftware.orpheusplaylist.data.util.extensions.isPlaying
import com.artemissoftware.orpheusplaylist.data.visualizer.VisualizerHelper
import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.domain.models.Audio
import com.artemissoftware.orpheusplaylist.domain.models.visualizer.VisualizerData
import com.artemissoftware.orpheusplaylist.domain.usecases.CreateUserPlaylistUseCase
import com.artemissoftware.orpheusplaylist.domain.usecases.GetFavoriteTracksUseCase
import com.artemissoftware.orpheusplaylist.domain.usecases.UpdateUserPlaylistUseCase
import com.artemissoftware.orpheusplaylist.utils.OrpheusConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrpheusPlaylistViewModel @Inject constructor(
    private val createUserPlaylistUseCase: CreateUserPlaylistUseCase,
    private val updateUserPlaylistUseCase: UpdateUserPlaylistUseCase,
    private val getFavoriteTracksUseCase: GetFavoriteTracksUseCase,
    serviceConnection: MediaPlayerServiceConnection,
) : ViewModel() {

    private val _state = MutableStateFlow(OrpheusPlaylistState())
    val state: StateFlow<OrpheusPlaylistState> = _state.asStateFlow()

    lateinit var rootMediaId: String

    private val playbackState = serviceConnection.playBackState
    private val currentPlayingAudio = serviceConnection.currentPlayingAudio

    val currentPlaying: Audio?
        get() = serviceConnection.currentPlayingAudio.value

    val isAudioPlaying: Boolean
        get() = playbackState.value?.isPlaying == true

    private val _visualizerHelper = VisualizerHelper()
    private val _visualizerData = mutableStateOf(value = VisualizerData.emptyVisualizerData())
    val visualizerData: State<VisualizerData> = _visualizerData

    /**
     * To help get the connection between the media browser and the media service
     */
    private val serviceConnection = serviceConnection.also {
        updatePlayBack()
    }

    private var updatePosition = true

    /**
     * To subscribe to media browser
     */
    private val subscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
            super.onChildrenLoaded(parentId, children)
        }
    }

    init {
        createUserPlayList()
    }

    fun onTriggerEvent(event: OrpheusPlaylistEvents) {
        when (event) {
            is OrpheusPlaylistEvents.PreLoadAlbum -> { addPlaylist(album = event.album) }
            is OrpheusPlaylistEvents.PlayAudio -> { playAudio(currentAudio = event.track) }
            is OrpheusPlaylistEvents.PlayTrack -> {
                playTrack()
            }
            is OrpheusPlaylistEvents.SwipePlayTrack -> {
                swipePlayTrack(audio = event.track)
            }
            is OrpheusPlaylistEvents.SeekTo -> { seekTo(value = event.value) }
            OrpheusPlaylistEvents.SkipToNext -> { skipToNext() }
            OrpheusPlaylistEvents.SkipToPrevious -> { skipToPrevious() }
            is OrpheusPlaylistEvents.TogglePlayerDisplay -> { updatePlayerDisplay(event.isFullDisplay) }
            is OrpheusPlaylistEvents.UpdateUserPlaylist -> {
                updateUserPlaylist(audioId = event.audioId)
            }
            is OrpheusPlaylistEvents.UpdateAudioIndex -> {
                updateIndex(_state.value.audioList.indexOf(event.track))
            }
        }
    }

    private fun addPlaylist(album: Album) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    preLoadedAlbum = album,
                )
            }

            checkMediaPlayerServiceConnection()
        }
    }

    private suspend fun checkMediaPlayerServiceConnection() {
        serviceConnection.isConnected.collect { result ->
            when (result) {
                is MediaResource.Success -> {
                    rootMediaId = serviceConnection.rootMediaId
                    serviceConnection.playBackState.value?.apply {
                        _state.update {
                            it.copy(currentPlayBackPosition = position)
                        }
                    }
                    serviceConnection.subscribe(rootMediaId, subscriptionCallback)
                }
                is MediaResource.Error -> TODO()
                is MediaResource.Idle -> Unit
            }
        }
    }

    private fun playAudio(currentAudio: Audio) {
        loadAlbum(index = _state.value.audioList.indexOf(currentAudio))
        serviceConnection.playAudio(_state.value.audioList)

        if (currentAudio.id == currentPlayingAudio.value?.id) {
            playTrack()
        } else {
            playTrack(audio = currentAudio)
            startVisualizer()
        }
    }

    private fun playTrack() {
        if (isAudioPlaying) {
            serviceConnection.transportControl.pause()
        } else {
            serviceConnection.transportControl.play()
        }
    }

    private fun playTrack(audio: Audio) {
        serviceConnection.transportControl.playFromMediaId(audio.id.toString(), null)
        updateIndex(_state.value.audioList.indexOf(audio))
    }

    private fun swipePlayTrack(audio: Audio) = with(_state) {
        if (value.fullPlayer) {
            if (isAudioPlaying) {
                serviceConnection.transportControl.playFromMediaId(audio.id.toString(), null)
            } else {
                serviceConnection.transportControl.prepareFromMediaId(audio.id.toString(), null)
            }
            updateIndex(value.audioList.indexOf(audio))
        }
    }

    private fun loadAlbum(index: Int) = with(_state) {
        value.preLoadedAlbum?.let { preLoadedAlbum ->

            serviceConnection.refreshMediaBrowserChildren(preLoadedAlbum.tracks)

            update {
                it.copy(
                    currentAudioIndex = index,
                    preLoadedAlbum = null,
                    loadedAlbum = preLoadedAlbum,
                    audioList = preLoadedAlbum.tracks,
                )
            }
        }
    }

    private fun skipToNext() = with(_state.value) {
        serviceConnection.skipToNext()
        updateIndex(if (audioList.size <= currentAudioIndex + 1) currentAudioIndex else currentAudioIndex + 1)
    }

    private fun skipToPrevious() = with(_state.value) {
        serviceConnection.skipToPrevious()
        updateIndex(if (currentAudioIndex - 1 < 0) currentAudioIndex else currentAudioIndex - 1)
    }

    private fun seekTo(value: Float) {
        val currentDuration = currentDuration
        serviceConnection.transportControl.seekTo(
            (currentDuration * value / 100f).toLong(),
        )
    }

    private fun updatePlayerDisplay(fullDisplay: Boolean) = with(_state) {
        update {
            it.copy(
                fullPlayer = fullDisplay,
            )
        }
    }

    private fun updateIndex(index: Int) = with(_state) {
        update {
            it.copy(
                currentAudioIndex = index,
            )
        }
    }

    private fun updatePlayBack() {
        viewModelScope.launch {
            val position = playbackState.value?.currentPosition ?: 0
            val currentDuration = currentDuration

            _state.update {
                val currentPlayBackPosition = if (it.currentPlayBackPosition != position) position else it.currentPlayBackPosition
                val currentAudioProgress = if (currentDuration > 0) (currentPlayBackPosition.toFloat() / currentDuration.toFloat() * 100f) else it.currentAudioProgress

                it.copy(currentPlayBackPosition = currentPlayBackPosition, currentAudioProgress = currentAudioProgress)
            }

            delay(MediaPlayerServiceConstants.PLAYBACK_UPDATE_INTERVAL)
            if (updatePosition) {
                updatePlayBack()
            }
        }
    }

    private fun startVisualizer() {
        serviceConnection.run {
            _visualizerHelper.start(
                audioSessionId = audioSessionId,
                onData = { data ->
                    _visualizerData.value = data
                },
            )
        }
    }

    private fun createUserPlayList() = with(_state) {
        viewModelScope.launch {
            createUserPlaylistUseCase(playlistName = OrpheusConstants.USER_PLAYLIST_ALBUM_NAME)

            getFavoriteTracksUseCase.invoke(playlistName = OrpheusConstants.USER_PLAYLIST_ALBUM_NAME).collectLatest { favorites ->
                update {
                    it.copy(
                        favorites = favorites,
                    )
                }
            }
        }
    }

    private fun updateUserPlaylist(audioId: Long) {
        viewModelScope.launch {
            updateUserPlaylistUseCase(audioId = audioId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        _visualizerHelper.stop()
        serviceConnection.unSubscribe(object : MediaBrowserCompat.SubscriptionCallback() {})
        updatePosition = false
    }
}
