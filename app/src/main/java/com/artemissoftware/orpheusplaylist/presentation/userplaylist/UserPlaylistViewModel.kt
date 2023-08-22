package com.artemissoftware.orpheusplaylist.presentation.userplaylist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.orpheusplaylist.domain.usecases.GetAlbumUserPlaylistUseCase
import com.artemissoftware.orpheusplaylist.domain.usecases.UpdateUserPlaylistUseCase
import com.artemissoftware.orpheusplaylist.utils.OrpheusConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPlaylistViewModel @Inject constructor(
    private val getAlbumUserPlaylistUseCase: GetAlbumUserPlaylistUseCase,
    private val updateUserPlaylistUseCase: UpdateUserPlaylistUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(UserPlaylistState())
    val state: StateFlow<UserPlaylistState> = _state.asStateFlow()

    init {
        val albumId = savedStateHandle.get<Long>("albumId")
        albumId?.let {
            getAlbumUserPlaylist(OrpheusConstants.USER_PLAYLIST_ALBUM_NAME)
        }
    }

    fun onTriggerEvent(event: UserPlayListEvents) {
        when (event) {
            is UserPlayListEvents.UpdateUserPlaylist -> {
                updateUserPlaylist(audioId = event.audioId)
            }
        }
    }

    private fun getAlbumUserPlaylist(playlistName: String) {
        viewModelScope.launch {
            getAlbumUserPlaylistUseCase(playlistName = playlistName).collectLatest { album ->
                _state.update {
                    it.copy(album = album)
                }
            }
        }
    }

    private fun updateUserPlaylist(audioId: Long) {
        viewModelScope.launch {
            updateUserPlaylistUseCase(audioId = audioId)
            removeTrack(audioId = audioId)
        }
    }

    private fun removeTrack(audioId: Long) = with(_state) {
        val tracks = value.album?.tracks?.toMutableList() ?: mutableListOf()

        tracks.removeIf { it.id == audioId }

        update {
            it.copy(album = it.album?.copy(tracks = tracks))
        }
    }
}
