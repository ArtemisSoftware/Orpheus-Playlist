package com.artemissoftware.orpheusplaylist.presentation.composables.tracks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.domain.models.Album
import com.artemissoftware.orpheusplaylist.domain.models.Audio

@Composable
fun TrackList(
    onTrackClick: (Audio) -> Unit,
    onUpdateUserPlaylist: (Long) -> Unit,
    modifier: Modifier = Modifier,
    showAddToPlaylist: Boolean = true,
    selectedTrack: Audio? = null,
    album: Album? = null,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    Box(modifier = modifier) {
        album?.let { currentAlbum ->

            if (currentAlbum.tracks.isNotEmpty()) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    items(currentAlbum.tracks) { track ->
                        Track(
                            showAddToPlaylist = showAddToPlaylist,
                            audio = track,
                            isPlaying = track.id == selectedTrack?.id,
                            onClick = {
                                onTrackClick.invoke(it)
                            },
                            onUpdateUserPlaylist = onUpdateUserPlaylist,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(68.dp),
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(140.dp))
                    }
                }
            } else {
                WarningMessage(
                    message = stringResource(id = R.string.tracks_not_found),
                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
private fun WarningMessage(
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
private fun TrackListPreview() {
    TrackList(
        onTrackClick = {},
        onUpdateUserPlaylist = { },
        modifier = Modifier
            .fillMaxWidth(),
        selectedTrack = DummyData.audio,
        album = DummyData.album,
    )
}

@Preview(showBackground = true)
@Composable
private fun TrackList_no_track_selected_Preview() {
    TrackList(
        onTrackClick = {},
        onUpdateUserPlaylist = { audioId -> },
        modifier = Modifier
            .fillMaxWidth(),
        album = DummyData.album,
    )
}

@Preview(showBackground = true)
@Composable
private fun TrackList_no_tracks_Preview() {
    TrackList(
        onTrackClick = {},
        onUpdateUserPlaylist = { audioId -> },
        modifier = Modifier
            .fillMaxWidth(),
        album = DummyData.albumNoTracks,
    )
}
