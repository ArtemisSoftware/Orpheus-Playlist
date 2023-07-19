package com.artemissoftware.orpheusplaylist.presentation.playlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.AlbumBanner
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.Track

@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel = hiltViewModel(),
    addPlaylist: (List<AudioMetadata>) -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = state.album){
        state.album?.let { album -> addPlaylist(album.tracks) }
    }

    PlaylistContent(
        state = state,
    )
}

@Composable
private fun PlaylistContent(
    state: PlaylistState,
) {
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
                        .weight(2f)
                        .padding(horizontal = 16.dp),
                ) {
                    items(album.tracks) { track ->
                        Track(
                            audio = track,
                            onClick = {
                            },
                            modifier = Modifier.fillMaxWidth().height(68.dp),
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
private fun PlaylistContentPreview() {
    PlaylistContent(state = PlaylistState(album = DummyData.album))
}

@Preview(showBackground = true)
@Composable
private fun PlaylistContent_no_album_Preview() {
    PlaylistContent(state = PlaylistState(album = null))
}
