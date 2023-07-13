package com.artemissoftware.orpheusplaylist.headphone.presentation.playlist.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.headphone.data.model.AudioMetadata

@Composable
fun Track(
    audio: AudioMetadata,
    onClick: (AudioMetadata) -> Unit,
    modifier: Modifier = Modifier,
    isPlaying: Boolean, // TODO: adicionar isto para meter a bold a musica que está a tocar no momento
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                onClick(audio)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.mp3_logo),
                contentDescription = "",
                modifier = Modifier
                    .size(size = 60.dp)
                    .padding(all = 5.dp),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = audio.songTitle,
                    modifier = Modifier.padding(bottom = 3.dp),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    color = if (isPlaying) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.onBackground
                    },
                )
                Text(
                    text = audio.artist,
                    color = if (isPlaying) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.onBackground
                    },
                )
            }
        }

        if (isPlaying) {
            Icon(
                painter = painterResource(id = R.drawable.chart_simple_solid),
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(end = 8.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TrackPreview() {
    Track(audio = AudioMetadata(), onClick = {}, isPlaying = false)
}
