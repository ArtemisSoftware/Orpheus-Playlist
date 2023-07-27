package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.utils.extensions.toDuration

@Composable
fun PlayerControllerDisplay(
    progress: Float,
    playBackPosition: Long,
    isAudioPlaying: Boolean,
    duration: String,
    onPlay: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onProgressChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    displayColor: Color = Color.Black,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = progress,
            onValueChange = { onProgressChange.invoke(it) },
            valueRange = 0f..100f,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = playBackPosition.toInt().toDuration(),
                color = displayColor,
                style = MaterialTheme.typography.h5,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = duration,
                color = displayColor,
                style = MaterialTheme.typography.h5,
            )
        }

        AudioControllerDisplay(
            isPlaying = isAudioPlaying,
            onBack = onPrevious,
            displayColor = displayColor,
            onFastBackward = {},
            onPlay = onPlay,
            onFastForward = {},
            onNext = onNext,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerControllerDisplayPreview() {
    PlayerControllerDisplay(
        progress = 30F,
        isAudioPlaying = true,
        duration = "100",
//        isAudioPlaying = true,
        onPlay = {},
        onPrevious = {},
        onNext = {},
        onProgressChange = {},
        playBackPosition = 35L,
    )
}
