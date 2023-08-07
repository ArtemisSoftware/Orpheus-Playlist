package com.artemissoftware.orpheusplaylist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistEvents
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistViewModel
import com.artemissoftware.orpheusplaylist.presentation.albums.AlbumScreen
import com.artemissoftware.orpheusplaylist.presentation.playlist.PlaylistScreen
import com.artemissoftware.orpheusplaylist.presentation.userplaylist.UserPlaylistScreen
import com.artemissoftware.orpheusplaylist.utils.OrpheusConstants

/*
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = "navigation_graph",
        startDestination = Screens.Albums.fullRoute(),
    ) {
        composable(route = Screens.Albums.fullRoute()) {
            AlbumScreen(
                onPlaylistClick = { albumId ->
                    navController.navigate(route = Screens.Playlist.withArgs(albumId.toString()))
                },
            )
        }

        composable(
            route = Screens.Playlist.fullRoute(Screens.Playlist.albumId),
            arguments = Screens.Playlist.arguments,
        ) {
            PlaylistScreen()
        }
    }
}
*/
@Composable
fun NavigationGraph(navController: NavHostController) {
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
                        if(albumId == OrpheusConstants.USER_PLAYLIST_ALBUM_ID){
                            navController.navigate(route = Screens.UserPlaylist.withArgs(albumId.toString()))
                        }
                        else {
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
                        //viewModel.onTriggerEvent(OrpheusPlaylistEvents.SkipToPrevious)
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
                        //viewModel.onTriggerEvent(OrpheusPlaylistEvents.SkipToPrevious)
                    },
                    togglePlayerDisplay = { isFullDisplay ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.TogglePlayerDisplay(isFullDisplay))
                    },
                    onProgressChange = { progress ->
                        viewModel.onTriggerEvent(OrpheusPlaylistEvents.SeekTo(progress))
                    },
                )
            }

            /*
            composable("personal_details") { entry ->
                val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
                val state by viewModel.sharedState.collectAsState()

                PersonalDetailsScreen(
                    sharedState = state,
                    onNavigate = {
                        viewModel.updateState()
                        navController.navigate("terms_and_conditions")
                    },
                )
            }
            composable("terms_and_conditions") { entry ->
                val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
                val state by viewModel.sharedState.collectAsState()

                TermsAndConditionsScreen(
                    sharedState = state,
                    onOnboardingFinished = {
                        navController.navigate(route = "other_screen") {
                            popUpTo("onboarding") {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }
        composable("other_screen") {
            Text(text = "Hello world")
        }
        */
        }
    }
}
