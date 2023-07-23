package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
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
    title: String? = null,
    onCollapse: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        elevation = 0.dp,
        title = {
            title?.let {
                Text(
                    text = "name",
                    style = MaterialTheme.typography.body2,
                )
            }
        },
        backgroundColor = Color.Transparent,
        navigationIcon = {
            IconButton(onClick = { onCollapse.invoke() }) {
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Back Arrow",
                    tint = Color.Green,
                )
            }
        },
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
