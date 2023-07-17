package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.Duration

@Composable
fun PlayerControllerDisplay(
    ofHours: Duration?,
    modifier: Modifier = Modifier,
) {
    if (ofHours != null) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = 0f,
                onValueChange = {},
                colors = SliderDefaults.colors(
                    thumbColor = Color.Green,
                    activeTrackColor = Color.Red,
                ),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "0s", color = Color.Blue)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${ofHours.seconds}s", color = Color.Blue)
            }

            Spacer(modifier = Modifier.height(8.dp))

            AudioControllerDisplay(
                isPlaying = true,
                onBack = {},
                onFastBackward = {},
                onStart = {},
                onFastForward = {},
                onNext = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerControllerDisplayPreview() {
    PlayerControllerDisplay(
        Duration.ofHours(2),
//        isAudioPlaying = true,
//        onStart = {},
//        onNext = {},
    )
}
