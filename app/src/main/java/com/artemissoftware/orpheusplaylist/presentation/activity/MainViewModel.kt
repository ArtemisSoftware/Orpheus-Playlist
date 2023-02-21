package com.artemissoftware.orpheusplaylist.presentation.activity

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    //--private val repository: HeadphonePlayerRepository
) : ViewModel() {


    private var _player: MediaPlayer? = null

    private fun initPlayer(context: Context, uri: Uri) {
        _player = MediaPlayer().apply {
            setDataSource(context, uri)
            prepare()
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