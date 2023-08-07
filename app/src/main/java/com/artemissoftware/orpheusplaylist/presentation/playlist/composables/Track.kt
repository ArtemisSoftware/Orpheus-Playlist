package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.RemoveDone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onUpdateUserPlaylist: (Long) -> Unit,
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    showAddToPlaylist: Boolean = true,
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
            modifier = Modifier.weight(0.15F),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Light,
        )

        Text(
            text = audio.name,
            modifier = Modifier.weight(0.7F),
            style = if (isPlaying) MaterialTheme.typography.h4 else MaterialTheme.typography.body1,
            fontWeight = if (isPlaying) FontWeight.ExtraBold else FontWeight.Light,
            overflow = TextOverflow.Ellipsis,
        )

        if (showAddToPlaylist) {
            IconButton(onClick = { onUpdateUserPlaylist.invoke(audio.id) }) {
                Icon(
                    imageVector = if (audio.isOnPlaylist) Icons.Default.RemoveDone else Icons.Default.PlaylistAdd,
                    contentDescription = "Add list",
                    tint = Color.White,
                )
            }
        }
        else {
            IconButton(onClick = { onUpdateUserPlaylist.invoke(audio.id) }) {
                Icon(
                    imageVector = Icons.Default.RemoveDone,
                    contentDescription = "Add list",
                    tint = Color.White,
                )
            }
        }

        Text(
            text = audio.timeStampToDuration(),
            modifier = Modifier.weight(0.2F),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Light,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Track_is_playing_Preview() {
    Track(
        audio = DummyData.audioMetadata,
        onClick = {},
        onUpdateUserPlaylist = {},
        modifier = Modifier.fillMaxWidth().height(60.dp),
        isPlaying = true,
        showAddToPlaylist = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun Track_not_playing_Preview() {
    Track(
        audio = DummyData.audioMetadata,
        onClick = {},
        onUpdateUserPlaylist = {},
        modifier = Modifier.fillMaxWidth().height(60.dp),
        showAddToPlaylist = false,
    )
}
