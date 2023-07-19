package com.artemissoftware.orpheusplaylist.presentation.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.orpheusplaylist.domain.usecases.GetAlbumUseCase
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
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(PlaylistState())
    val state: StateFlow<PlaylistState> = _state.asStateFlow()

    init {
        val albumId = savedStateHandle.get<Long>("albumId")
        albumId?.let { getAlbum(albumId = it) }
    }

    private fun getAlbum(albumId: Long) {
        viewModelScope.launch {
            val result = getAlbumUseCase(albumId = albumId)

            _state.update {
                it.copy(album = result)
            }
        }
    }
}
