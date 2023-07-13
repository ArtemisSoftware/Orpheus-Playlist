package com.artemissoftware.orpheusplaylist.player.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PlayerButtons(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 42.dp,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        val buttonModifier = Modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }

        Image(
            imageVector = Icons.Filled.SkipPrevious,
            contentDescription = "Skip Previous",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier,
        )

        Image(
            imageVector = Icons.Filled.Replay10,
            contentDescription = "Reply 10 Second",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier,
        )

        Image(
            imageVector = Icons.Filled.PlayCircleFilled,
            contentDescription = "Play",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(playerButtonSize)
                .semantics { role = Role.Button },
        )

        Image(
            imageVector = Icons.Filled.Forward10,
            contentDescription = "Forward 10 Seconds",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier,
        )

        Image(
            imageVector = Icons.Filled.SkipNext,
            contentDescription = "Skip Next",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = buttonModifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerButtonsPreview() {
    PlayerButtons(modifier = Modifier.fillMaxWidth())
}
