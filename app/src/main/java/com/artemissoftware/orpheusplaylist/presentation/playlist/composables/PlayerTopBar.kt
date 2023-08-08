package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.RemoveDone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.data.models.AlbumType

@Composable
fun PlayerTopBar(
    showAddToPlayList: Boolean,
    isOnUserPlayList: Boolean,
    onCollapse: () -> Unit,
    onUpdateUserPlaylist: () -> Unit,
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
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    style = MaterialTheme.typography.body2,
                )
            }
        },
        backgroundColor = Color.Transparent,
        actions = {
            if(showAddToPlayList) {
                IconButton(onClick = { onUpdateUserPlaylist.invoke() }) {
                    Icon(
                        imageVector = if (isOnUserPlayList) Icons.Default.RemoveDone else Icons.Default.PlaylistAdd,
                        contentDescription = "Add list",
                        tint = tint,
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PlayerTopBarPreview() {
    PlayerTopBar(
        isOnUserPlayList = true,
        onCollapse = {},
        onUpdateUserPlaylist = {},
        showAddToPlayList = true,
    )
}
