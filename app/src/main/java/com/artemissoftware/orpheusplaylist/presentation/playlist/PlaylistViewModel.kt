package com.artemissoftware.orpheusplaylist.presentation.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.domain.usecases.GetAlbumUseCase
import com.artemissoftware.orpheusplaylist.domain.usecases.GetAlbumUserPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getAlbumUseCase: GetAlbumUseCase,
    private val getAlbumUserPlaylistUseCase: GetAlbumUserPlaylistUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(PlaylistState())
    val state: StateFlow<PlaylistState> = _state.asStateFlow()

    init {
        val albumId = savedStateHandle.get<Long>("albumId")
        albumId?.let { getAlbum(albumId = it) }
    }

    fun onTriggerEvent(event: PlayListEvents) {
        when (event) {
            is PlayListEvents.SelectTrack -> {
                selectTrack(track = event.track)
            }

            PlayListEvents.SkipToNextTrack -> {
                skipToNext()
            }

            PlayListEvents.SkipToPreviousTrack -> {
                skipToPrevious()
            }
        }
    }

    private fun getAlbum(albumId: Long) {
        viewModelScope.launch {
            val result = getAlbumUseCase(albumId = albumId)

            _state.update {
                it.copy(
                    album = result,
                    albumCover = result?.albumMetadata?.uri,
                    type = AlbumType.ALBUM,
                )
            }
        }
    }

    private fun getAlbumUserPlaylist(playlistName: String) {
        viewModelScope.launch {
            val result = getAlbumUserPlaylistUseCase(playlistName = playlistName)

            _state.update {
                it.copy(album = result)
            }
        }
    }

    private fun selectTrack(track: AudioMetadata) = with(_state) {
        update {
            it.copy(selectedTrack = track)
        }
    }

    private fun skipToNext() = with(_state) {
        val trackListSize = value.album?.tracks?.size ?: 0

        val index = if (trackListSize == (getCurrentTrackIndex() + 1)) 0 else (getCurrentTrackIndex() + 1)

        if (trackListSize != 0) {
            value.album?.tracks?.let { list ->
                update { playState ->
                    playState.copy(selectedTrack = list[index], selectedTrackIndex = index)
                }
            }
        }
    }

    private fun skipToPrevious() = with(_state) {
        val trackListSize = value.album?.tracks?.size ?: 0

        val index = if ((getCurrentTrackIndex() - 1) < 0) 0 else (getCurrentTrackIndex() - 1)

        if (trackListSize != 0) {
            value.album?.tracks?.let { list ->
                update { playState ->
                    playState.copy(selectedTrack = list[index], selectedTrackIndex = index)
                }
            }
        }
    }

    private fun getCurrentTrackIndex(): Int {
        var index = 0

        with(_state.value) {
            val currentIndex = album?.tracks?.indexOf(selectedTrack) ?: 0
            index = if (currentIndex == -1) 0 else currentIndex
        }

        return index
    }
}
