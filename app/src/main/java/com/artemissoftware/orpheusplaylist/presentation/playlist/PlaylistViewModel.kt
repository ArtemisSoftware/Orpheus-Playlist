package com.artemissoftware.orpheusplaylist.presentation.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.orpheusplaylist.data.models.AlbumType
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.domain.usecases.GetAlbumUseCase
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
class PlaylistViewModel @Inject constructor(
    private val getAlbumUseCase: GetAlbumUseCase,
    private val getAlbumUserPlaylistUseCase: GetAlbumUserPlaylistUseCase,
    private val updateUserPlaylistUseCase: UpdateUserPlaylistUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(PlaylistState())
    val state: StateFlow<PlaylistState> = _state.asStateFlow()

    init {
        val albumId = savedStateHandle.get<Long>("albumId")
        albumId?.let {
            if (it == OrpheusConstants.USER_PLAYLIST_ALBUM_ID) {
                getAlbumUserPlaylist(OrpheusConstants.USER_PLAYLIST_ALBUM_NAME)
            } else {
                getAlbum(albumId = it)
            }
        }
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

            is PlayListEvents.UpdateUserPlaylist -> {
                updateUserPlaylist(audioId = event.audioId)
            }

            PlayListEvents.UpdateTracks -> {
                getAlbumUserPlaylist(OrpheusConstants.USER_PLAYLIST_ALBUM_NAME)
            }
        }
    }

    private fun getAlbum(albumId: Long) {
        viewModelScope.launch {
            getAlbumUseCase(albumId = albumId).collectLatest { album ->
                _state.update {
                    it.copy(
                        album = album,
                        albumCover = album?.albumMetadata?.uri,
                        type = AlbumType.ALBUM,
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

    private fun getAlbumUserPlaylist(playlistName: String) {
        viewModelScope.launch {
            getAlbumUserPlaylistUseCase(playlistName = playlistName).collectLatest { album ->
                _state.update {
                    it.copy(album = album)
                }
            }
        }
    }

    private fun selectTrack(track: AudioMetadata) = with(_state) {
        if (track.id != value.selectedTrack?.id) {
            update {
                it.copy(selectedTrack = track)
            }
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
