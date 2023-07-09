package com.artemissoftware.orpheusplaylist.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import java.time.Duration

@Composable
fun PlayerSlider(ofHours: Duration?) {
    if (ofHours != null) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Slider(
                value = 0f,
                onValueChange = {},
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                ),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "0s", color = Color.White)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${ofHours.seconds}s", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerSliderPreview() {
    PlayerSlider(ofHours = Duration.ofHours(2))
}
