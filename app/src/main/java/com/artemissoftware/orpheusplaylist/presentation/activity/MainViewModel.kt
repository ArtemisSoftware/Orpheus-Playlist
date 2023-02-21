package com.artemissoftware.orpheusplaylist.presentation.activity

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.orpheusplaylist.domain.model.AudioMetadata
import com.artemissoftware.orpheusplaylist.domain.repository.AudioPlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AudioPlayerRepository
) : ViewModel() {


    private var _player: MediaPlayer? = null

    init {
        loadMedias()
    }

    private fun initPlayer(context: Context, uri: Uri) {
        _player = MediaPlayer().apply {
            setDataSource(context, uri)
            prepare()
        }
    }

     fun loadMedias() {
        viewModelScope.launch {
//            _state = _state.copy(isLoading = true)
            val audios = mutableStateListOf<AudioMetadata>()
            val lolo = prepareAudios()
            audios.addAll(lolo)
//            _state = _state.copy(audios = audios)
//            repository.getLikedSongs().collect { likedSongs ->
//                _state = _state.copy(
//                    likedSongs = likedSongs,
//                    isLoading = false,
//                )
//            }
        }
    }

    private suspend fun prepareAudios(): List<AudioMetadata> {
        return repository.getAudios().map {
            val artist = if (it.artist.contains("<unknown>"))
                "Unknown artist" else it.artist
            it.copy(artist = artist)
        }
    }


    private fun play() {
        _player?.start()
    }


    private fun pause() {
        _player?.pause()
    }

    private fun stop() {
        _player?.stop()
        _player?.reset()
        _player?.release()
        _player = null
    }

    private fun seek(position: Float) {
        _player?.run {
            seekTo(position.toInt())
        }
    }

}