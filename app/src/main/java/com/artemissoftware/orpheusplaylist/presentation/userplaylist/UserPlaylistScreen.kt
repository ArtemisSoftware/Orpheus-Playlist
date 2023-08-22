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
import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.domain.models.Audio
import com.artemissoftware.orpheusplaylist.presentation.composables.album.AlbumBanner
import com.artemissoftware.orpheusplaylist.presentation.composables.tracks.TrackList

@Composable
fun UserPlaylistScreen(
    viewModel: UserPlaylistViewModel = hiltViewModel(),
    preLoadAlbum: (Album) -> Unit,
    onPlayAudio: (Audio) -> Unit,
    playerState: OrpheusPlaylistState,
    currentPlaying: Audio?,
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
    currentPlaying: Audio?,
    state: UserPlaylistState,
    events: (UserPlayListEvents) -> Unit,
    onPlayAudio: (Audio) -> Unit,
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
        currentPlaying = DummyData.audio,
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
        currentPlaying = DummyData.audio,
    )
}
