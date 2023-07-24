package com.artemissoftware.orpheusplaylist.presentation.playlist

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetCollapsed
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetContent
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetExpanded
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.AlbumBanner
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.Track
import com.artemissoftware.orpheusplaylist.utils.extensions.currentFraction
import kotlinx.coroutines.launch

@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel = hiltViewModel(),
    addPlaylist: (List<AudioMetadata>) -> Unit,
    onPlayAudio: (AudioMetadata) -> Unit,
    onSkipToNext: () -> Unit,
    onProgressChange: (Float) -> Unit,
    playerState: OrpheusPlaylistState,
    isAudioPlaying: Boolean,
) {
    val state = viewModel.state.collectAsState().value

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
) {
    val coroutineScope = rememberCoroutineScope()

    val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 140.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            SheetContent {
                SheetExpanded {
                    PlayerPage(
                        tracks = state.album?.tracks ?: emptyList(),
                        selectedTrack = state.selectedTrack,
                        playerState = playerState,
                        isAudioPlaying = isAudioPlaying,
                        onProgressChange = onProgressChange,
                        onPlay = onPlayAudio,
                        onSkipToNext = onSkipToNext,
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
                        onPlay = onPlayAudio,
                        onSkipToNext = {
                            events.invoke(PlayListEvents.SkipToNextTrack)
                            onSkipToNext.invoke()
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
                                        onPlayAudio.invoke(it)
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
private fun PlaylistScreenContentPreview() {
    PlaylistScreenContent(
        state = PlaylistState(album = DummyData.album, selectedTrack = DummyData.audioMetadata),
        playerState = OrpheusPlaylistState(),
        isAudioPlaying = true,
        events = {},
        onPlayAudio = {},
        onProgressChange = {},
        onSkipToNext = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun PlaylistScreenContent_no_album_Preview() {
    PlaylistScreenContent(
        state = PlaylistState(album = null),
        playerState = OrpheusPlaylistState(),
        isAudioPlaying = false,
        events = {},
        onPlayAudio = {},
        onProgressChange = {},
        onSkipToNext = {},
    )
}
