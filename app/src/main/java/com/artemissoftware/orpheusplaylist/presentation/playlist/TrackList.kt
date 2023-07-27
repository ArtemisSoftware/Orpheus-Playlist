package com.artemissoftware.orpheusplaylist.presentation.playlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.artemissoftware.orpheusplaylist.data.models.Album
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.Track

@Composable
fun TrackList(
    album: Album,
    onTrackClick: (AudioMetadata) -> Unit,
    modifier: Modifier = Modifier,
    selectedTrack: AudioMetadata? = null,
) {
    if (album.tracks.isNotEmpty()) {
        LazyColumn(modifier = modifier) {
            items(album.tracks) { track ->
                Track(
                    audio = track,
                    isPlaying = track.id == selectedTrack?.id,
                    onClick = {
                        onTrackClick.invoke(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp),
                )
            }
        }
    } else {
        WarningMessage(
            message = stringResource(id = R.string.tracks_not_found),
            modifier = modifier,
        )
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
        album = DummyData.album,
        selectedTrack = DummyData.audioMetadata,
        onTrackClick = {},
        modifier = Modifier
            .fillMaxWidth(),
    )
}

@Preview(showBackground = true)
@Composable
private fun TrackList_no_track_selected_Preview() {
    TrackList(
        album = DummyData.album,
        onTrackClick = {},
        modifier = Modifier
            .fillMaxWidth(),
    )
}

@Preview(showBackground = true)
@Composable
private fun TrackList_no_tracks_Preview() {
    TrackList(
        album = DummyData.albumNoTracks,
        onTrackClick = {},
        modifier = Modifier
            .fillMaxWidth(),
    )
}
