package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.presentation.composables.MediaDescription

@Composable
fun MediaControllerDisplay(
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(null)
            .size(Size.ORIGINAL)
            .error(R.drawable.musical_note_music_svgrepo_com)
            .placeholder(R.drawable.musical_note_music_svgrepo_com)
            .build(),
    )

    Card(
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth().height(128.dp),
    ) {
        AsyncImage(
            model = painter.request,
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.4F)
                .blur(2.dp),
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Card(
                    modifier = Modifier,
                    elevation = 4.dp,
                ) {
                    AsyncImage(
                        model = painter.request,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null,
                        modifier = Modifier
                            .size(56.dp)
                            .weight(2F),
                    )
                }

                MediaDescription(
                    title = "title",
                    name = "name",
                    textColor = Color.Black,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(5F),
                )

                AudioControllerDisplay(
                    modifier = Modifier.weight(3F),
                    isPlaying = false,
                    onStart = {},
                    onNext = {},
                )
            }

            Slider(
                value = 30F,
                onValueChange = { },
                valueRange = 0f..100f,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaControllerDisplayPreview() {
    MediaControllerDisplay(
//        isAudioPlaying = true,
//        onStart = {},
//        onNext = {},
    )
}