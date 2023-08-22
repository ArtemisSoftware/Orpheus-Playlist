package com.artemissoftware.orpheusplaylist.presentation.playlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistState
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.domain.models.Audio
import com.artemissoftware.orpheusplaylist.presentation.composables.tracks.TrackList
import com.artemissoftware.orpheusplaylist.presentation.composables.album.AlbumBanner

@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel = hiltViewModel(),
    preLoadAlbum: (Album) -> Unit,
    onPlayAudio: (Audio) -> Unit,
    playerState: OrpheusPlaylistState,
    currentPlaying: Audio?,
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = state.album?.id) {
        state.album?.let { album -> preLoadAlbum(album) }
    }

    PlaylistScreenContent(
        playerState = playerState,
        state = state,
        events = viewModel::onTriggerEvent,
        onPlayAudio = onPlayAudio,
        currentPlaying = currentPlaying,
    )
}

@Composable
private fun PlaylistScreenContent(
    playerState: OrpheusPlaylistState,
    currentPlaying: Audio?,
    state: PlaylistState,
    events: (PlayListEvents) -> Unit,
    onPlayAudio: (Audio) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        AlbumBanner(
            album = playerState.getAlbum(),
            modifier = Modifier.fillMaxWidth().weight(1f),
        )

        TrackList(
            lazyListState = lazyListState,
            album = state.album,
            onTrackClick = {
                onPlayAudio.invoke(it)
            },
            onUpdateUserPlaylist = { audioId ->
                events.invoke(PlayListEvents.AddTrackToFavorites(audioId = audioId))
            },
            selectedTrack = currentPlaying,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaylistScreenContentPreview() {
    PlaylistScreenContent(
        playerState = OrpheusPlaylistState(),
        state = PlaylistState(album = DummyData.album),
        events = {},
        onPlayAudio = {},
        currentPlaying = DummyData.audio,
    )
}

@Preview(showBackground = true)
@Composable
private fun PlaylistScreenContent_no_album_Preview() {
    PlaylistScreenContent(
        playerState = OrpheusPlaylistState(),
        state = PlaylistState(album = null),
        events = {},
        onPlayAudio = {},
        currentPlaying = DummyData.audio,
    )
}
