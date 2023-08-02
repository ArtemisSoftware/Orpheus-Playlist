package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AudioControllerDisplay(
    isPlaying: Boolean,
    onPlay: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: Dp = 56.dp,
    displayColor: Color = Color.Yellow,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        PlayerButton(
            icon = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
            enabled = true,
            onClick = onPlay,
            size = buttonSize,
            tint = displayColor,
        )
        PlayerButton(
            icon = Icons.Filled.SkipNext,
            enabled = true,
            onClick = onNext,
            size = buttonSize,
            tint = displayColor,
        )
    }
}

@Composable
fun AudioControllerDisplay(
    isPlaying: Boolean,
    onBack: () -> Unit,
    onFastBackward: () -> Unit,
    onPlay: () -> Unit,
    onFastForward: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
    displayColor: Color = Color.Yellow,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        PlayerButton(
            icon = Icons.Filled.SkipPrevious,
            onClick = onBack,
            size = 56.dp,
            elevation = 0.dp,
            color = Color.Transparent,
            tint = displayColor,
        )
        PlayerButton(
            icon = Icons.Filled.Replay10,
            onClick = onFastBackward,
            size = 56.dp,
            elevation = 0.dp,
            color = Color.Transparent,
        )

        PlayerButton(
            icon = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
            enabled = true,
            onClick = onPlay,
            tint = Color.Black,
            color = displayColor,
            size = 68.dp,
        )
        PlayerButton(
            icon = Icons.Filled.Forward10,
            onClick = onFastForward,
            size = 56.dp,
            elevation = 0.dp,
            color = Color.Transparent,
        )
        PlayerButton(
            icon = Icons.Filled.SkipNext,
            onClick = onNext,
            size = 56.dp,
            elevation = 0.dp,
            color = Color.Transparent,
            tint = displayColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AudioControllerDisplayPreview() {
    AudioControllerDisplay(
        isPlaying = true,
        onPlay = {},
        onNext = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun AudioControllerDisplay_full_Preview() {
    AudioControllerDisplay(
        isPlaying = true,
        onBack = {},
        onFastBackward = {},
        onPlay = {},
        onFastForward = {},
        onNext = {},
    )
}
