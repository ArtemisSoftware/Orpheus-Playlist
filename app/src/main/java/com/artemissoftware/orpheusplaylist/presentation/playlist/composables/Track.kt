package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata

@Composable
fun Track(
    audio: AudioMetadata,
    onClick: (AudioMetadata) -> Unit,
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
) {
    Row(
        modifier = modifier
            .clickable {
                onClick(audio)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = audio.position.track.toString(),
            modifier = Modifier.weight(0.1F),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Light,
        )

        Text(
            text = audio.name,
            modifier = Modifier.weight(0.7F),
            style = MaterialTheme.typography.body1,
            fontWeight = if (isPlaying) FontWeight.ExtraBold else FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = audio.duration.toString(),
            modifier = Modifier.weight(0.2F),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Light,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Track_is_playing_Preview() {
    Track(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        audio = DummyData.audioMetadata,
        onClick = {},
        isPlaying = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun Track_not_playing_Preview() {
    Track(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        audio = DummyData.audioMetadata,
        onClick = {},
    )
}
