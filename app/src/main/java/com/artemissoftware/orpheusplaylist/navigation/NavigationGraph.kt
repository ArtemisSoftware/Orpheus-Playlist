package com.artemissoftware.orpheusplaylist.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistEvents
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistState
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistViewModel
import com.artemissoftware.orpheusplaylist.data.models.Album
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.headphone.util.audio.VisualizerData
import com.artemissoftware.orpheusplaylist.presentation.albums.AlbumScreen
import com.artemissoftware.orpheusplaylist.presentation.playlist.PlaylistScreen
import com.artemissoftware.orpheusplaylist.presentation.userplaylist.UserPlaylistScreen
import com.artemissoftware.orpheusplaylist.utils.OrpheusConstants

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    playerState: OrpheusPlaylistState,
    currentPlaying: AudioMetadata?,
    isAudioPlaying: Boolean,
    visualizerData: State<VisualizerData>,
    preLoadAlbum: (Album) -> Unit,
    onPlayAudio: (AudioMetadata) -> Unit,
    onProgressChange: (Float) -> Unit,
    onSwipePlayTrack: (AudioMetadata) -> Unit,
    onSkipToNext: () -> Unit,
    togglePlayerDisplay: (Boolean) -> Unit,
    onSkipToPrevious: () -> Unit,

    ) {
    NavHost(
        navController = navController,
        route = "lolo_navigation_graph",
        startDestination = Screens.Albums.fullRoute(),
    ) {
        composable(route = Screens.Albums.fullRoute()) {
            AlbumScreen(
                onPlaylistClick = { albumId ->
                    if (albumId == OrpheusConstants.USER_PLAYLIST_ALBUM_ID) {
                        navController.navigate(route = Screens.UserPlaylist.withArgs(albumId.toString()))
                    } else {
                        navController.navigate(route = Screens.Playlist.withArgs(albumId.toString()))
                    }
                },
            )
        }

        composable(
            route = Screens.Playlist.fullRoute(Screens.Playlist.albumId),
            arguments = Screens.Playlist.arguments,
        ) { entry ->

            PlaylistScreen(
                playerState = playerState,
                currentPlaying = currentPlaying,
                isAudioPlaying = isAudioPlaying,
                visualizer = visualizerData,
                preLoadAlbum = preLoadAlbum,
                onPlayAudio = onPlayAudio,
                onPlayTrack = {
                    // viewModel.onTriggerEvent(OrpheusPlaylistEvents.PlayTrack)
                },
                onSwipePlayTrack = { track ->
                    // viewModel.onTriggerEvent(OrpheusPlaylistEvents.SwipePlayTrack(track))
                },
                onSkipToNext = onSkipToNext,
                onSkipToPrevious = {
                    // viewModel.onTriggerEvent(OrpheusPlaylistEvents.SkipToPrevious)
                },
                togglePlayerDisplay = togglePlayerDisplay,
                onProgressChange = onProgressChange,
            )
        }
    }
}

@Composable
fun NavigationGraph_(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = "navigation_graph",
        startDestination = "orpheus",
    ) {
        navigation(
            startDestination = Screens.Albums.fullRoute(),
            route = "orpheus",
        ) {
            composable(route = Screens.Albums.fullRoute()) {
                AlbumScreen(
                    onPlaylistClick = { albumId ->
                        if (albumId == OrpheusConstants.USER_PLAYLIST_ALBUM_ID) {
                            navController.navigate(route = Screens.UserPlaylist.withArgs(albumId.toString()))
                        } else {
                            navController.navigate(route = Screens.Playlist.withArgs(albumId.toString()))
                        }
                    },
                )
            }

            composable(
                route = Screens.Playlist.fullRoute(Screens.Playlist.albumId),
                arguments = Screens.Playlist.arguments,
            ) { entry ->

                val viewModel = entry.sharedViewModel<OrpheusPlaylistViewModel>(navController)
                val state = viewModel.state.collectAsState().value

                PlaylistScreen(
                    playerState = state,
                    currentPlaying = viewModel.currentPlaying,
                    isAudioPlaying = viewModel.isAudioPlaying,
                    visualizer = viewModel.visualizerData,
                    preLoadAlbum = { album ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.PreLoadAlbum(album = album))
                    },
                    onPlayAudio = { track ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.PlayAudio(track = track))
                    },
                    onPlayTrack = {
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.PlayTrack)
                    },
                    onSwipePlayTrack = { track ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.SwipePlayTrack(track))
                    },
                    onSkipToNext = {
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.SkipToNext)
                    },
                    onSkipToPrevious = {
                        // viewModel.onTriggerEvent(OrpheusPlaylistEvents.SkipToPrevious)
                    },
                    togglePlayerDisplay = { isFullDisplay ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.TogglePlayerDisplay(isFullDisplay))
                    },
                    onProgressChange = { progress ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.SeekTo(progress))
                    },
                )
            }

            composable(
                route = Screens.UserPlaylist.fullRoute(Screens.UserPlaylist.albumId),
                arguments = Screens.UserPlaylist.arguments,
            ) { entry ->

                val viewModel = entry.sharedViewModel<OrpheusPlaylistViewModel>(navController)
                val state = viewModel.state.collectAsState().value

                UserPlaylistScreen(
                    playerState = state,
                    currentPlaying = viewModel.currentPlaying,
                    isAudioPlaying = viewModel.isAudioPlaying,
                    visualizer = viewModel.visualizerData,
                    preLoadAlbum = { album ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.PreLoadAlbum(album = album))
                    },
                    onPlayAudio = { track ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.PlayAudio(track = track))
                    },
                    onPlayTrack = {
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.PlayTrack)
                    },
                    onSwipePlayTrack = { track ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.SwipePlayTrack(track))
                    },
                    onSkipToNext = {
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.SkipToNext)
                    },
                    onSkipToPrevious = {
                        // viewModel.onTriggerEvent(OrpheusPlaylistEvents.SkipToPrevious)
                    },
                    togglePlayerDisplay = { isFullDisplay ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.TogglePlayerDisplay(isFullDisplay))
                    },
                    onProgressChange = { progress ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.SeekTo(progress))
                    },
                )
            }
        }
    }
}
