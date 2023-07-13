package com.artemissoftware.orpheusplaylist.playaudio.presentation.composables.player

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.playaudio.data.models.Audio
import com.artemissoftware.orpheusplaylist.playaudio.presentation.composables.DummyAudio

@Composable
fun ArtistInfo(
    audio: Audio,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PlayerIcon(
            icon = Icons.Default.MusicNote,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface,
            ),
            onClick = {},
        )

        Spacer(modifier = Modifier.size(4.dp))

        Column {
            Text(
                text = audio.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Clip,
                modifier = Modifier.weight(1f),
                maxLines = 1,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = audio.artist,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Clip,
                maxLines = 1,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArtistInfoPreview() {
    ArtistInfo(
        audio = DummyAudio.audioList[0],
    )
}
