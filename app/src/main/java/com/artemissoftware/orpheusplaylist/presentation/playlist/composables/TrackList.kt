package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.data.model.AudioMetadata
import com.artemissoftware.orpheusplaylist.presentation.activity.AudioPlayerState

@Composable
fun TrackList(
    state: AudioPlayerState,
    onClick: (AudioMetadata) -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.lbl_tracks),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h3,
            modifier = Modifier
                .padding(
                    bottom = 3.dp,
                    top = 12.dp,
                ),
            textDecoration = TextDecoration.Underline,
            color = MaterialTheme.colors.onBackground,
        )
    }

    state.audios.forEach { audio ->
        Track(
            audio = audio,
            isPlaying = audio.songId == state.selectedAudio.songId,
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 10.dp,
                )
                .requiredHeight(height = 100.dp),
            onClick = onClick,
        )
        Divider(modifier = Modifier.padding(horizontal = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun TrackListPreview() {
    TrackList(AudioPlayerState())
}
