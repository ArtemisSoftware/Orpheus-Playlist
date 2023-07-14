package com.artemissoftware.orpheusplaylist.presentation.albums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.presentation.composables.MediaCard

@Composable
fun AlbumScreen(viewModel: AlbumViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value

    AlbumScreenContent(
        state = state,
    )
}

@Composable
private fun AlbumScreenContent(
    state: AlbumState,
) {
    val stateLazyVerticalGrid = rememberLazyGridState()
    LazyVerticalGrid(
        state = stateLazyVerticalGrid,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(space = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
        contentPadding = PaddingValues(
//            start = 12.dp,
//            top = 16.dp,
//            end = 12.dp,
//            bottom = 16.dp,
        ),
        content = {
            items(state.albums) { album ->
                MediaCard(
                    album = album,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun AlbumScreenContentPreview() {
    AlbumScreenContent(state = AlbumState(albums = DummyData.listAlbumMetadata))
}
