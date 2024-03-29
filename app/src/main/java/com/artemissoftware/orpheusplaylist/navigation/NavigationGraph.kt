package com.artemissoftware.orpheusplaylist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistState
import com.artemissoftware.orpheusplaylist.data.models.AlbumType
import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.domain.models.Audio
import com.artemissoftware.orpheusplaylist.presentation.albums.AlbumScreen
import com.artemissoftware.orpheusplaylist.presentation.playlist.PlaylistScreen
import com.artemissoftware.orpheusplaylist.presentation.userplaylist.UserPlaylistScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    playerState: OrpheusPlaylistState,
    currentPlaying: Audio?,
    preLoadAlbum: (Album) -> Unit,
    onPlayAudio: (Audio) -> Unit,
) {
    NavHost(
        navController = navController,
        route = "navigation_graph",
        startDestination = Screens.Albums.fullRoute(),
    ) {
        composable(route = Screens.Albums.fullRoute()) {
            AlbumScreen(
                onPlaylistClick = { album ->
                    if (album.type == AlbumType.USER_PLAYLIST_ALBUM) {
                        navController.navigate(route = Screens.UserPlaylist.withArgs(album.id.toString()))
                    } else {
                        navController.navigate(route = Screens.Playlist.withArgs(album.id.toString()))
                    }
                },
            )
        }

        composable(
            route = Screens.Playlist.fullRoute(Screens.Playlist.albumId),
            arguments = Screens.Playlist.arguments,
        ) {
            PlaylistScreen(
                playerState = playerState,
                currentPlaying = currentPlaying,
                preLoadAlbum = preLoadAlbum,
                onPlayAudio = onPlayAudio,
            )
        }

        composable(
            route = Screens.UserPlaylist.fullRoute(Screens.UserPlaylist.albumId),
            arguments = Screens.UserPlaylist.arguments,
        ) {
            UserPlaylistScreen(
                playerState = playerState,
                currentPlaying = currentPlaying,
                preLoadAlbum = preLoadAlbum,
                onPlayAudio = onPlayAudio,
            )
        }
    }
}
