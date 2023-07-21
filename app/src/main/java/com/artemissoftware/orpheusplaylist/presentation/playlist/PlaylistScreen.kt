package com.artemissoftware.orpheusplaylist.presentation.playlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistState
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.playaudio.presentation.AudioPlaylistEvents
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.AlbumBanner
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.MediaControllerDisplay
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.Track

@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel = hiltViewModel(),
    addPlaylist: (List<AudioMetadata>) -> Unit,
    playAudio: (AudioMetadata) -> Unit,
    onProgressChange: (Float) -> Unit,
    playerState: OrpheusPlaylistState,
    isAudioPlaying: Boolean,
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = state.album) {
        state.album?.let { album -> addPlaylist(album.tracks) }
    }

    PlaylistContent(
        playerState = playerState,
        state = state,
        events = viewModel::onTriggerEvent,
        playAudio = playAudio,
        onProgressChange = onProgressChange,
        isAudioPlaying = isAudioPlaying,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PlaylistContent(
    playerState: OrpheusPlaylistState,
    isAudioPlaying: Boolean,
    state: PlaylistState,
    events: (PlayListEvents) -> Unit,
    onProgressChange: (Float) -> Unit,
    playAudio: (AudioMetadata) -> Unit,
) {
//    val coroutineScope = rememberCoroutineScope()

    val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Expanded)
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
//        sheetPeekHeight = 140.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                AlbumBanner(
                    album = state.album?.albumMetadata,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                )

                state.album?.let { album ->

                    if (album.tracks.isEmpty()) {
                        WarningMessage(
                            message = stringResource(id = R.string.tracks_not_found),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f),
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f),
                        ) {
                            items(album.tracks) { track ->
                                Track(
                                    audio = track,
                                    isPlaying = track.id == state.selectedTrack?.id,
                                    onClick = {
                                        events.invoke(PlayListEvents.SelectTrack(track = track))
                                        playAudio.invoke(it)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(68.dp),
                                )
                            }
                        }
                    }
                } ?: run {
                    WarningMessage(
                        message = stringResource(id = R.string.album_not_found),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                    )
                }
            }
        },
        sheetContent = {
            state.selectedTrack?.let { track ->
                MediaControllerDisplay(
                    isAudioPlaying = isAudioPlaying,
                    progress = playerState.currentAudioProgress,
                    onProgressChange = {
                        onProgressChange.invoke(it)
                    },
                    track = track,
                    onStart = {},
                    onNext = {},
                    modifier = Modifier
                        .padding(horizontal = 0.dp)
                        .padding(top = 0.dp, bottom = 0.dp),
                )
            }
        },
    )
}

@Composable private
fun WarningMessage(
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = message,
            style = MaterialTheme.typography.h5,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaylistContentPreview() {
    PlaylistContent(
        state = PlaylistState(album = DummyData.album, selectedTrack = DummyData.audioMetadata),
        playerState = OrpheusPlaylistState(),
        isAudioPlaying = true,
        events = {},
        playAudio = {},
        onProgressChange = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun PlaylistContent_no_album_Preview() {
    PlaylistContent(
        state = PlaylistState(album = null),
        playerState = OrpheusPlaylistState(),
        isAudioPlaying = false,
        events = {},
        playAudio = {},
        onProgressChange = {},
    )
}
