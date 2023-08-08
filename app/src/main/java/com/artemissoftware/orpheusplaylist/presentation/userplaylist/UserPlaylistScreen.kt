package com.artemissoftware.orpheusplaylist.presentation.userplaylist

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.artemissoftware.orpheusplaylist.data.models.Album
import com.artemissoftware.orpheusplaylist.data.models.AlbumType
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.headphone.util.audio.VisualizerData
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetCollapsed
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetContent
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetExpanded
import com.artemissoftware.orpheusplaylist.presentation.playlist.PlayListEvents
import com.artemissoftware.orpheusplaylist.presentation.playlist.PlayerBar
import com.artemissoftware.orpheusplaylist.presentation.playlist.PlayerPage
import com.artemissoftware.orpheusplaylist.presentation.playlist.PlaylistState
import com.artemissoftware.orpheusplaylist.presentation.playlist.PlaylistViewModel
import com.artemissoftware.orpheusplaylist.presentation.playlist.TrackList
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.AlbumBanner
import com.artemissoftware.orpheusplaylist.utils.extensions.currentFraction
import kotlinx.coroutines.launch

@Composable
fun UserPlaylistScreen(
    viewModel: PlaylistViewModel = hiltViewModel(),
    preLoadAlbum: (Album) -> Unit,
    onPlayAudio: (AudioMetadata) -> Unit,
    onPlayTrack: () -> Unit,
    onSwipePlayTrack: (AudioMetadata) -> Unit,
    onSkipToNext: () -> Unit,
    onSkipToPrevious: () -> Unit,
    togglePlayerDisplay: (Boolean) -> Unit,
    onProgressChange: (Float) -> Unit,
    playerState: OrpheusPlaylistState,
    isAudioPlaying: Boolean,
    visualizer: State<VisualizerData>,
    currentPlaying: AudioMetadata?,
) {
    val state = viewModel.state.collectAsState().value
    val visualizerData = visualizer.value

    LaunchedEffect(key1 = state.album?.albumMetadata?.id) {
        state.album?.let { album -> preLoadAlbum(album) }
    }

    UserPlaylistScreenContent(
        playerState = playerState,
        state = state,
        events = viewModel::onTriggerEvent,
        onPlayAudio = onPlayAudio,
        onPlayTrack = onPlayTrack,
        onSwipePlayTrack = onSwipePlayTrack,
        onProgressChange = onProgressChange,
        currentPlaying = currentPlaying,
        isAudioPlaying = isAudioPlaying,
        onSkipToNext = onSkipToNext,
        onSkipToPrevious = onSkipToPrevious,
        togglePlayerDisplay = togglePlayerDisplay,
        visualizerData = visualizerData,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UserPlaylistScreenContent(
    playerState: OrpheusPlaylistState,
    currentPlaying: AudioMetadata?,
    isAudioPlaying: Boolean,
    state: PlaylistState,
    events: (PlayListEvents) -> Unit,
    onProgressChange: (Float) -> Unit,
    onPlayAudio: (AudioMetadata) -> Unit,
    onPlayTrack: () -> Unit,
    onSwipePlayTrack: (AudioMetadata) -> Unit,
    onSkipToNext: () -> Unit,
    onSkipToPrevious: () -> Unit,
    togglePlayerDisplay: (Boolean) -> Unit,
    visualizerData: VisualizerData,
) {
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    val seekHeight by remember(currentPlaying) {
        mutableStateOf(if (currentPlaying == null) 0.dp else 140.dp)
    }

    LaunchedEffect(key1 = scaffoldState.bottomSheetState.currentValue) {
        togglePlayerDisplay.invoke(scaffoldState.bottomSheetState.isExpanded)
    }

    val album = (playerState.preLoadedAlbum?.albumMetadata ?: playerState.loadedAlbum?.albumMetadata)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                AlbumBanner(
                    albumName = album?.name ?: "",
                    artistName = "${(playerState.preLoadedAlbum?.tracks?.size ?: 0)} tracks",
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    cover = album?.uri,
                )

                TrackList(
                    showAddToPlaylist = false,
                    lazyListState = lazyListState,
                    album = state.album,
                    onTrackClick = {
                        events.invoke(PlayListEvents.SelectTrack(track = it))
                        onPlayAudio.invoke(it)
                    },
                    onUpdateUserPlaylist = { audioId ->
                        events.invoke(PlayListEvents.UpdateUserPlaylist(audioId = audioId))
                        events.invoke(PlayListEvents.RemoveTrackFromPlaylist(audioId = audioId))
                    },
                    selectedTrack = currentPlaying,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                )
            }
        },
        sheetPeekHeight = seekHeight,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            SheetContent {
                SheetExpanded {
                    PlayerPage(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = state,
                        playerState = playerState,
                        currentPlaying = currentPlaying,
                        isAudioPlaying = isAudioPlaying,
                        onProgressChange = onProgressChange,
                        visualizerData = visualizerData,
                        onPlay = { audio ->
//                            onPlayAudio.invoke(it)
                        },
                        onPlayTrack = {
                            onPlayTrack.invoke()
                        },
                        onSwipePlay = { track ->
                            if (playerState.fullPlayer) {
                                coroutineScope.launch {
                                    val currentIndex =
                                        playerState.loadedAlbum?.tracks?.indexOf(track) ?: 0
                                    val index = if (currentIndex == -1) 0 else currentIndex
                                    lazyListState.scrollToItem(index = index)
                                }

                                onSwipePlayTrack.invoke(track)
                            }
                        },
                        onSkipToPrevious = {
//                            events.invoke(PlayListEvents.SkipToPreviousTrack)
//                            onSkipToPrevious.invoke()
                        },
                        onSkipToNext = {
//                            events.invoke(PlayListEvents.SkipToNextTrack)
                            onSkipToNext.invoke()
                        },
                        onUpdateUserPlaylist = { audioId ->
                            events.invoke(PlayListEvents.UpdateUserPlaylist(audioId = audioId))
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
                        cover = if (state.type == AlbumType.ALBUM) playerState.loadedAlbum?.albumMetadata?.uri else currentPlaying?.albumMetadata?.uri,
                        currentPlaying = currentPlaying,
                        isAudioPlaying = isAudioPlaying,
                        onProgressChange = onProgressChange,
                        onPlay = {
                            onPlayTrack.invoke()
                        },
                        onSkipToNext = {
//                            events.invoke(PlayListEvents.SkipToNextTrack)
                            onSkipToNext.invoke()
                        },
                    )
                }
            }
        },

    )
}

@Preview(showBackground = true)
@Composable
private fun UserPlaylistScreenContentPreview() {
    UserPlaylistScreenContent(
        playerState = OrpheusPlaylistState(),
        isAudioPlaying = true,
        state = PlaylistState(album = DummyData.album, selectedTrack = DummyData.audioMetadata),
        events = {},
        onProgressChange = {},
        onPlayAudio = {},
        onPlayTrack = {},
        onSwipePlayTrack = {},
        onSkipToNext = {},
        onSkipToPrevious = {},
        togglePlayerDisplay = {},
        visualizerData = VisualizerData(),
        currentPlaying = DummyData.audioMetadata,
    )
}

@Preview(showBackground = true)
@Composable
private fun UserPlaylistScreenContent_no_album_Preview() {
    UserPlaylistScreenContent(
        playerState = OrpheusPlaylistState(),
        isAudioPlaying = false,
        state = PlaylistState(album = null),
        events = {},
        onProgressChange = {},
        onPlayAudio = {},
        onPlayTrack = {},
        onSwipePlayTrack = {},
        onSkipToNext = {},
        onSkipToPrevious = {},
        togglePlayerDisplay = {},
        visualizerData = VisualizerData(),
        currentPlaying = DummyData.audioMetadata,
    )
}