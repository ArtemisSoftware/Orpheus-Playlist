package com.artemissoftware.orpheusplaylist.presentation.playlist

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
) : ViewModel() {

    private val _state = MutableStateFlow(PlaylistState())
    val state: StateFlow<PlaylistState> = _state.asStateFlow()

    init {
        //getAlbum(albumId = 1000000965L)
        getAlbum(albumId = 7089781950825107897L)
        //getAlbum(albumId = 7L)
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
