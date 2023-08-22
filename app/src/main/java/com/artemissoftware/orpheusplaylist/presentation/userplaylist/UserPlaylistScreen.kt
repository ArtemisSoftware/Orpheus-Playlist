package com.artemissoftware.orpheusplaylist.presentation.userplaylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistState
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.presentation.composables.TrackList
import com.artemissoftware.orpheusplaylist.presentation.composables.player.AlbumBanner

@Composable
fun UserPlaylistScreen(
    viewModel: UserPlaylistViewModel = hiltViewModel(),
    preLoadAlbum: (Album) -> Unit,
    onPlayAudio: (AudioMetadata) -> Unit,
    playerState: OrpheusPlaylistState,
    currentPlaying: AudioMetadata?,
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = state.album?.id) {
        state.album?.let { album -> preLoadAlbum(album) }
    }

    UserPlaylistScreenContent(
        playerState = playerState,
        state = state,
        events = viewModel::onTriggerEvent,
        onPlayAudio = onPlayAudio,
        currentPlaying = currentPlaying,
    )
}

@Composable
private fun UserPlaylistScreenContent(
    playerState: OrpheusPlaylistState,
    currentPlaying: AudioMetadata?,
    state: UserPlaylistState,
    events: (UserPlayListEvents) -> Unit,
    onPlayAudio: (AudioMetadata) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        AlbumBanner(
            albumName = playerState.getAlbum()?.name ?: "",
            artistName = "${(state.album?.tracks?.size ?: 0)} " + stringResource(id = R.string.lbl_tracks),
            modifier = Modifier.fillMaxWidth().weight(1f),
            cover = playerState.getAlbum()?.uri,
        )

        TrackList(
            showAddToPlaylist = false,
            lazyListState = lazyListState,
            album = state.album,
            onTrackClick = {
                onPlayAudio.invoke(it)
            },
            onUpdateUserPlaylist = { audioId ->
                events.invoke(UserPlayListEvents.UpdateUserPlaylist(audioId = audioId))
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
private fun UserPlaylistScreenContentPreview() {
    UserPlaylistScreenContent(
        playerState = OrpheusPlaylistState(),
        state = UserPlaylistState(album = DummyData.album),
        events = {},
        onPlayAudio = {},
        currentPlaying = DummyData.audioMetadata,
    )
}

@Preview(showBackground = true)
@Composable
private fun UserPlaylistScreenContent_no_album_Preview() {
    UserPlaylistScreenContent(
        playerState = OrpheusPlaylistState(),
        state = UserPlaylistState(album = null),
        events = {},
        onPlayAudio = {},
        currentPlaying = DummyData.audioMetadata,
    )
}
