package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FeaturedPlayList
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PlayerTopBar(
    onCollapse: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    tint: Color = Color.Black,
) {
    TopAppBar(
        modifier = modifier,
        elevation = 0.dp,
        navigationIcon = {
            OutlinedButton(
                onClick = { onCollapse.invoke() },
                modifier = Modifier
                    .size(32.dp),
                shape = CircleShape,
                border = BorderStroke((0.4).dp, tint),
                contentPadding = PaddingValues(0.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Back Arrow",
                    tint = tint,
                )
            }
        },
        title = {
            title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body2,
                )
            }
        },
        backgroundColor = Color.Transparent,
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.FeaturedPlayList,
                    contentDescription = "Add list",
                    tint = Color.Green,
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.PlaylistAdd,
                    contentDescription = "Add list",
                    tint = Color.Green,
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PlayerTopBarPreview() {
    PlayerTopBar(
        onCollapse = {},
    )
}
