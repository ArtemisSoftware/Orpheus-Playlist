package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.ui.theme.Gray400
import com.artemissoftware.orpheusplaylist.ui.theme.onSurface

@Composable
fun PlayerButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 72.dp,
    elevation: Dp = 8.dp,
    color: Color = Color.Black,
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(size), // avoid the oval shape
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        elevation = ButtonDefaults.elevation(elevation),
        contentPadding = PaddingValues(0.dp), // avoid the little icon
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
                .background(color = color)
                .padding(all = 8.dp),
            tint = if (enabled) onSurface else Gray400,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerButtonPreview() {
    PlayerButton(
        icon = Icons.Filled.PlayArrow,
        enabled = true,
        onClick = {},
    )
}
