package com.artemissoftware.orpheusplaylist.playaudio.presentation.composables.player

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PlayerIcon(
    icon: ImageVector,
    border: BorderStroke? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    color: Color = MaterialTheme.colors.onSurface,
) {
    Surface(
        shape = CircleShape,
        border = border,
        modifier = modifier
            .clip(CircleShape)
            .clickable {
                onClick.invoke()
            },
        contentColor = color,
        color = backgroundColor,

    ) {
        Box(
            modifier = Modifier.padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerIconPreview() {
    PlayerIcon(
        icon = Icons.Default.Add,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface,
        ),
        onClick = {},
    )
}
