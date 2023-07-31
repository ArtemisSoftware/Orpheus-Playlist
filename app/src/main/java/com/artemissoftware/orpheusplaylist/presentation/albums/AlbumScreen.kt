package com.artemissoftware.orpheusplaylist.presentation.albums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.presentation.composables.MediaCard

@Composable
fun AlbumScreen(
    viewModel: AlbumViewModel = hiltViewModel(),
    onPlaylistClick: (Long) -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    AlbumScreenContent(
        state = state,
        onPlaylistClick = onPlaylistClick,
    )
}

@Composable
private fun AlbumScreenContent(
    state: AlbumState,
    onPlaylistClick: (Long) -> Unit,
) {
    val stateLazyVerticalGrid = rememberLazyGridState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(id = R.string.albums),
            style = MaterialTheme.typography.h2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
        )

        LazyVerticalGrid(
            state = stateLazyVerticalGrid,
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
            contentPadding = PaddingValues(16.dp),
            content = {
                items(state.albums) { album ->
                    MediaCard(
                        album = album,
                        textColor = Color.White,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onPlaylistClick = onPlaylistClick,
                    )
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AlbumScreenContentPreview() {
    AlbumScreenContent(state = AlbumState(albums = DummyData.listAlbumMetadata), onPlaylistClick = {})
}
