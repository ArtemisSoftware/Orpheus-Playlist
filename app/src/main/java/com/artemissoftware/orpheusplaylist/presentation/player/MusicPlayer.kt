package com.artemissoftware.orpheusplaylist.presentation.player

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistEvents
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistState
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistViewModel
import com.artemissoftware.orpheusplaylist.data.models.Album
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.headphone.util.audio.VisualizerData
import com.artemissoftware.orpheusplaylist.navigation.NavigationGraph
import com.artemissoftware.orpheusplaylist.navigation.bottomPlayerHeight
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetCollapsed
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetContent
import com.artemissoftware.orpheusplaylist.presentation.composables.SheetExpanded
import com.artemissoftware.orpheusplaylist.presentation.playlist.PlayerBar
import com.artemissoftware.orpheusplaylist.utils.extensions.currentFraction

@Composable
fun MusicPlayer(
    viewModel: OrpheusPlaylistViewModel = hiltViewModel(),
) {
    with(viewModel) {
        val state = state.collectAsState().value

        MusicPlayerContent(
            playerState = state,
            currentPlaying = currentPlaying,
            isAudioPlaying = isAudioPlaying,
            visualizerData = visualizerData,
            preLoadAlbum = { album ->
                onTriggerEvent(OrpheusPlaylistEvents.PreLoadAlbum(album = album))
            },
            onPlayAudio = { track ->
                onTriggerEvent(OrpheusPlaylistEvents.PlayAudio(track = track))
            },
            onPlayTrack = {
                onTriggerEvent(OrpheusPlaylistEvents.PlayTrack)
            },
            onSwipePlayTrack = { track ->
                onTriggerEvent(OrpheusPlaylistEvents.SwipePlayTrack(track))
            },
            onSkipToNext = {
                onTriggerEvent(OrpheusPlaylistEvents.SkipToNext)
            },
            onSkipToPrevious = {
                onTriggerEvent(OrpheusPlaylistEvents.SkipToPrevious)
            },
            togglePlayerDisplay = { isFullDisplay ->
                onTriggerEvent(OrpheusPlaylistEvents.TogglePlayerDisplay(isFullDisplay))
            },
            onProgressChange = { progress ->
                onTriggerEvent(OrpheusPlaylistEvents.SeekTo(progress))
            },
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MusicPlayerContent(
    navController: NavHostController = rememberNavController(),
    playerState: OrpheusPlaylistState,
    currentPlaying: AudioMetadata?,
    isAudioPlaying: Boolean,
    visualizerData: State<VisualizerData>,
    preLoadAlbum: (Album) -> Unit,
    onPlayAudio: (AudioMetadata) -> Unit,
    onPlayTrack: () -> Unit,
    onSwipePlayTrack: (AudioMetadata) -> Unit,
    onSkipToNext: () -> Unit,
    onSkipToPrevious: () -> Unit,
    togglePlayerDisplay: (Boolean) -> Unit,
    onProgressChange: (Float) -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    val seekHeight by remember(currentPlaying) {
        mutableStateOf(if (currentPlaying == null) 0.dp else 140.dp)
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetPeekHeight = seekHeight,
        sheetContent = {
            SheetContent {
                SheetExpanded {
//                    PlayerPage(
//                        playerState = playerState,
//                        state = state,
//                        isAudioPlaying = isAudioPlaying,
//                        visualizerData = visualizerData,
//                        onProgressChange = onProgressChange,
//                        onCollapse = {
//                            coroutineScope.launch {
//                                scaffoldState.bottomSheetState.animateTo(
//                                    targetValue = BottomSheetValue.Collapsed,
//                                    tween(500),
//                                )
//                            }
//                        },
//                        onPlay = { audio ->
// //                            onPlayAudio.invoke(it)
//                        },
//                        onPlayTrack = {
//                            onPlayTrack.invoke()
//                        },
//                        onSwipePlay = { track ->
//                            if (playerState.fullPlayer) {
//                                coroutineScope.launch {
//                                    val currentIndex =
//                                        playerState.loadedAlbum?.tracks?.indexOf(track) ?: 0
//                                    val index = if (currentIndex == -1) 0 else currentIndex
//                                    lazyListState.scrollToItem(index = index)
//                                }
//
//                                onSwipePlayTrack.invoke(track)
//                            }
//                        },
//                        onSkipToNext = {
// //                            events.invoke(PlayListEvents.SkipToNextTrack)
//                            onSkipToNext.invoke()
//                        },
//                        onSkipToPrevious = {
// //                            events.invoke(PlayListEvents.SkipToPreviousTrack)
// //                            onSkipToPrevious.invoke()
//                        },
//                        onUpdateUserPlaylist = { audioId ->
//                            events.invoke(PlayListEvents.UpdateUserPlaylist(audioId = audioId))
//                        },
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        currentPlaying = currentPlaying,
//                    )
                }
                SheetCollapsed(
                    isCollapsed = scaffoldState.bottomSheetState.isCollapsed,
                    currentFraction = scaffoldState.currentFraction,
                    height = bottomPlayerHeight,
                ) {
                    PlayerBar(
                        playerState = playerState,
                        cover = playerState.getPlayerBarCover(currentPlaying),
                        currentPlaying = currentPlaying,
                        isAudioPlaying = isAudioPlaying,
                        onProgressChange = onProgressChange,
                        onPlay = {
                            onPlayTrack.invoke()
                        },
                        onSkipToNext = onSkipToNext,
                    )
                }
            }
        },
    ) {
        NavigationGraph(
            navController = navController,
            playerState = playerState,
            currentPlaying = currentPlaying,
            isAudioPlaying = isAudioPlaying,
            visualizerData = visualizerData,
            preLoadAlbum = preLoadAlbum,
            onPlayAudio = onPlayAudio,
            onSwipePlayTrack = onSwipePlayTrack,
            onSkipToNext = onSkipToNext,
            onSkipToPrevious = onSkipToPrevious,
            togglePlayerDisplay = togglePlayerDisplay,
            onProgressChange = onProgressChange,
        )
    }
}
