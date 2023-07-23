package com.artemissoftware.orpheusplaylist.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SheetCollapsed(
    isCollapsed: Boolean,
    height: Dp = 72.dp,
    currentFraction: Float,
    content: @Composable RowScope.() -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(fraction = 1F - currentFraction) // TODO: não estou 100% contente
            .wrapContentHeight()
            //.height(height)
            //.background(MaterialTheme.colors.primary)
            .graphicsLayer(alpha = 1f - currentFraction),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}
