package com.artemissoftware.orpheusplaylist.presentation.playlist

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistState
import com.artemissoftware.orpheusplaylist.data.models.AlbumType
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.MediaControllerDisplay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerBar(
    playerState: OrpheusPlaylistState,
    state: PlaylistState,
    isAudioPlaying: Boolean,
    onPlay: (AudioMetadata) -> Unit,
    onProgressChange: (Float) -> Unit,
    onSkipToNext: () -> Unit,
    currentPlaying: AudioMetadata? = null,
    cover: Bitmap? = null,
) {
//    val pagerState: PagerState = rememberPagerState()
//
//    HorizontalPager(
//        state = pagerState,
//        pageCount = 2,
//        modifier = Modifier.fillMaxWidth(),
//    ) { page: Int ->
//        state.selectedTrack?.let { track ->
//            MediaControllerDisplay(
//                isAudioPlaying = isAudioPlaying,
//                progress = playerState.currentAudioProgress,
//                onProgressChange = {
//                    onProgressChange.invoke(it)
//                },
//                track = track,
//                onPlay = onPlay,
//                onNext = onSkipToNext,
//                modifier = Modifier
//                    .padding(horizontal = 0.dp)
//                    .padding(top = 0.dp, bottom = 0.dp),
//            )
//        }
//    }

    currentPlaying?.let { track ->
        MediaControllerDisplay(
            isAudioPlaying = isAudioPlaying,
            progress = playerState.currentAudioProgress,
            onProgressChange = {
                onProgressChange.invoke(it)
            },
            cover = cover,
            track = track,
            onPlay = onPlay,
            onNext = onSkipToNext,
            modifier = Modifier
                .padding(horizontal = 0.dp)
                .padding(top = 0.dp, bottom = 0.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerBarPreview() {
    PlayerBar(
        playerState = OrpheusPlaylistState(),
        state = PlaylistState(album = DummyData.album, selectedTrack = DummyData.audioMetadata),
        isAudioPlaying = true,
        onPlay = {},
        onProgressChange = {},
        onSkipToNext = {},
        currentPlaying = null,
        cover = null,
    )
}
