package com.artemissoftware.orpheusplaylist.presentation.activity

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.orpheusplaylist.data.model.AudioMetadata
import com.artemissoftware.orpheusplaylist.domain.repository.AudioPlayerRepository
import com.artemissoftware.orpheusplaylist.presentation.playlist.AudioPlayerEvent
import com.artemissoftware.orpheusplaylist.util.audio.VisualizerData
import com.artemissoftware.orpheusplaylist.util.audio.VisualizerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AudioPlayerRepository,
) : ViewModel() {

    private var _state = MutableStateFlow(value = AudioPlayerState())
    val state = _state.asStateFlow()

    private var _player: MediaPlayer? = null
    private val _visualizerHelper = VisualizerHelper()

    private val _visualizerData = mutableStateOf(value = VisualizerData.emptyVisualizerData())
    val visualizerData: State<VisualizerData> = _visualizerData

    init {
        loadMedias()
    }

    fun onEvent(event: AudioPlayerEvent) {
        when (event) {
            is AudioPlayerEvent.InitAudio -> initAudio(
                audio = event.audio,
                context = event.context,
                onAudioInitialized = event.onAudioInitialized,
            )

            is AudioPlayerEvent.Seek -> seek(position = event.position)

            is AudioPlayerEvent.LikeOrNotSong -> "" // likeOrNotSong(id = event.id)

            AudioPlayerEvent.Pause -> pause()

            AudioPlayerEvent.Play -> play()

            AudioPlayerEvent.Stop -> stop()

            AudioPlayerEvent.HideLoadingDialog -> hideLoadingDialog()

            AudioPlayerEvent.LoadMedias -> loadMedias()
            else -> {}
        }
    }

    private fun initAudio(audio: AudioMetadata, context: Context, onAudioInitialized: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            delay(800)

            val cover = repository.loadCoverBitmap(
                context = context,
                uri = audio.contentUri,
            )

            _state.update { it.copy(selectedAudio = audio.copy(cover = cover)) }

            _player = MediaPlayer().apply {
                setDataSource(context, audio.contentUri)
                prepare()
            }

//            _player?.setOnCompletionListener {
//                pause()
//            }

            _player?.setOnPreparedListener {
                onAudioInitialized()
            }

            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun loadMedias() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val audios = mutableStateListOf<AudioMetadata>()
            audios.addAll(prepareAudios())
            _state.update { it.copy(audios = audios, isLoading = false) }
//             repository.getLikedSongs().collect { likedSongs ->
//                 _state = _state.copy(
//                     likedSongs = likedSongs,
//                     isLoading = false,
//                 )
//             }
        }
    }

    // TODO: quando juntar isto já é tratado
    private suspend fun prepareAudios(): List<AudioMetadata> {
        return repository.getAudios().map {
            val artist = if (it.artist.contains("<unknown>")) {
                "Unknown artist"
            } else {
                it.artist
            }
            it.copy(artist = artist)
        }
    }

    private fun play() {
        _state.update { it.copy(isPlaying = true) }

        _player?.let {
            it.start()
            it.run {
                _visualizerHelper.start(
                    audioSessionId = audioSessionId, // TODO: verificar como obter isto do exoplayer
                    onData = { data ->
                        _visualizerData.value = data
                    },
                )
            }
        }
    }

    private fun pause() {
        _state.update { it.copy(isPlaying = false) }
        _visualizerHelper.stop()
        _player?.pause()
    }

    private fun stop() {
        _visualizerHelper.stop()
        _player?.let {
            it.stop()
            it.reset()
            it.release()
        }
        _state.update { it.copy(isPlaying = false) }
        _player = null
    }

    private fun seek(position: Float) {
        _player?.run {
            seekTo(position.toInt())
        }
    }

    private fun hideLoadingDialog() {
        _state.update { it.copy(isLoading = false) }
    }
}
