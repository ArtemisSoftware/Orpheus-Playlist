package com.artemissoftware.orpheusplaylist.presentation.playlist

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistState
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.headphone.util.audio.VisualizerData
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetCollapsed
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetContent
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetExpanded
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.AlbumBanner
import com.artemissoftware.orpheusplaylist.utils.extensions.currentFraction
import kotlinx.coroutines.launch

@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel = hiltViewModel(),
    addPlaylist: (List<AudioMetadata>) -> Unit,
    onPlayAudio: (AudioMetadata) -> Unit,
    onSkipToNext: () -> Unit,
    onSkipToPrevious: () -> Unit,
    onProgressChange: (Float) -> Unit,
    playerState: OrpheusPlaylistState,
    isAudioPlaying: Boolean,
    visualizer: State<VisualizerData>,
) {
    val state = viewModel.state.collectAsState().value
    val visualizerData = visualizer.value

    LaunchedEffect(key1 = state.album) {
        state.album?.let { album -> addPlaylist(album.tracks) }
    }

    PlaylistScreenContent(
        playerState = playerState,
        state = state,
        events = viewModel::onTriggerEvent,
        onPlayAudio = onPlayAudio,
        onProgressChange = onProgressChange,
        isAudioPlaying = isAudioPlaying,
        onSkipToNext = onSkipToNext,
        onSkipToPrevious = onSkipToPrevious,
        visualizerData = visualizerData,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PlaylistScreenContent(
    playerState: OrpheusPlaylistState,
    isAudioPlaying: Boolean,
    state: PlaylistState,
    events: (PlayListEvents) -> Unit,
    onProgressChange: (Float) -> Unit,
    onPlayAudio: (AudioMetadata) -> Unit,
    onSkipToNext: () -> Unit,
    onSkipToPrevious: () -> Unit,
    visualizerData: VisualizerData,
) {
    val coroutineScope = rememberCoroutineScope()

    val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    val seekHeight by remember(state.selectedTrack) {
        mutableStateOf(if (state.selectedTrack == null) 0.dp else 140.dp)
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = seekHeight,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            SheetContent {
                SheetExpanded {
                    PlayerPage(
                        state = state,
                        playerState = playerState,
                        isAudioPlaying = isAudioPlaying,
                        onProgressChange = onProgressChange,
                        visualizerData = visualizerData,
                        onPlay = {
//                            onPlayAudio.invoke(it)
                        },
                        onSwipePlay = { track ->
//                            events.invoke(PlayListEvents.SelectTrack(track = track))
//                            onPlayAudio.invoke(track)
                        },
                        onSkipToPrevious = {
//                            events.invoke(PlayListEvents.SkipToPreviousTrack)
//                            onSkipToPrevious.invoke()
                        },
                        onSkipToNext = {
//                            events.invoke(PlayListEvents.SkipToNextTrack)
//                            onSkipToNext.invoke()
                        },
                        onCollapse = {
                            coroutineScope.launch {
                                scaffoldState.bottomSheetState.animateTo(
                                    targetValue = BottomSheetValue.Collapsed,
                                    tween(500),
                                )
                            }
                        },
                    )
                }
                SheetCollapsed(
                    isCollapsed = scaffoldState.bottomSheetState.isCollapsed,
                    currentFraction = scaffoldState.currentFraction,
                    height = 140.dp,
                ) {
                    PlayerBar(
                        playerState = playerState,
                        state = state,
                        isAudioPlaying = isAudioPlaying,
                        onProgressChange = onProgressChange,
                        onPlay = {
//                            onPlayAudio.invoke(it)
                        },
                        onSkipToNext = {
//                            events.invoke(PlayListEvents.SkipToNextTrack)
//                            onSkipToNext.invoke()
                        },
                    )
                }
            }
        },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                AlbumBanner(
                    album = state.album?.albumMetadata,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                )

                state.album?.let { album ->
                    TrackList(
                        album = album,
                        onTrackClick = {
                            events.invoke(PlayListEvents.SelectTrack(track = it))
                            onPlayAudio.invoke(it)
                        },
                        selectedTrack = state.selectedTrack,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PlaylistScreenContentPreview() {
    PlaylistScreenContent(
        playerState = OrpheusPlaylistState(),
        isAudioPlaying = true,
        state = PlaylistState(album = DummyData.album, selectedTrack = DummyData.audioMetadata),
        events = {},
        onProgressChange = {},
        onPlayAudio = {},
        onSkipToNext = {},
        onSkipToPrevious = {},
        visualizerData = VisualizerData(),
    )
}

@Preview(showBackground = true)
@Composable
private fun PlaylistScreenContent_no_album_Preview() {
    PlaylistScreenContent(
        playerState = OrpheusPlaylistState(),
        isAudioPlaying = false,
        state = PlaylistState(album = null),
        events = {},
        onProgressChange = {},
        onPlayAudio = {},
        onSkipToNext = {},
        onSkipToPrevious = {},
        visualizerData = VisualizerData(),
    )
}
