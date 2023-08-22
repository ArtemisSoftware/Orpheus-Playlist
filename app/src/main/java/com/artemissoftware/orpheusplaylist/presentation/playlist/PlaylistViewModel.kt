package com.artemissoftware.orpheusplaylist.presentation.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.orpheusplaylist.domain.usecases.GetAlbumUseCase
import com.artemissoftware.orpheusplaylist.domain.usecases.UpdateUserPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getAlbumUseCase: GetAlbumUseCase,
    private val updateUserPlaylistUseCase: UpdateUserPlaylistUseCase,
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
            is PlayListEvents.AddTrackToFavorites -> {
                addTrackToFavorites(audioId = event.audioId)
            }

            is PlayListEvents.RemoveTrackFromFavorites -> {
                removeTrackFromFavorites(audioId = event.audioId)
            }
        }
    }

    private fun getAlbum(albumId: Long) {
        viewModelScope.launch {
            getAlbumUseCase(albumId = albumId).collectLatest { album ->
                _state.update {
                    it.copy(
                        album = album,
                    )
                }
            }
        }
    }

    private fun removeTrackFromFavorites(audioId: Long) = with(_state) {
        val tracks = value.album?.tracks?.toMutableList() ?: mutableListOf()

        tracks.removeIf { it.id == audioId }

        update {
            it.copy(album = it.album?.copy(tracks = tracks))
        }
    }

    private fun addTrackToFavorites(audioId: Long) {
        viewModelScope.launch {
            updateUserPlaylistUseCase(audioId = audioId)
        }
    }
}
