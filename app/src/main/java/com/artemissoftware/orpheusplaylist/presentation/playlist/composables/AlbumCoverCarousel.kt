package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.presentation.playlist.lolo
import com.artemissoftware.orpheusplaylist.utils.extensions.carouselTransition
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumCoverCarousel(
    tracks: List<AudioMetadata>,
    onSwipePlay: (Int) -> Unit,
    index: Int,
    modifier: Modifier = Modifier,
) {
    val currentPage = remember {
        mutableStateOf(0)
    }

    val pagerState: PagerState = rememberPagerState()

    LaunchedEffect(key1 = index) {
        pagerState.animateScrollToPage(index)
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        currentPage.value = pagerState.currentPage
        delay(1000L)

        if (currentPage.value == pagerState.currentPage) {
            onSwipePlay.invoke(pagerState.currentPage)
        }
    }

    HorizontalPager(
        state = pagerState,
        pageCount = tracks.size,
        contentPadding = PaddingValues(
            horizontal = 60.dp,
        ),
        pageSpacing = 12.dp,
        modifier = modifier,
    ) { page: Int ->

        Card(
            modifier = Modifier
                .carouselTransition(page, pagerState),
        ) {
            AlbumCover(track = tracks[page])
        }
    }
}

@Composable
private fun AlbumCover(track: AudioMetadata) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(track.albumMetadata.uri)
            .size(Size.ORIGINAL)
            .error(R.drawable.musical_note_music_svgrepo_com)
            .placeholder(R.drawable.musical_note_music_svgrepo_com)
            .build(),
    )

    AsyncImage(
        model = painter.request,
        contentScale = ContentScale.FillBounds,
        contentDescription = null,
        modifier = Modifier.size(lolo),
    )
}

@Preview(showBackground = true)
@Composable
private fun AlbumCoverPreview() {
    Column(Modifier.fillMaxWidth()) {
        AlbumCoverCarousel(
            modifier = Modifier.fillMaxWidth(),
            tracks = DummyData.listAudioMetadata,
            onSwipePlay = {},
            index = 0,
        )
    }
}
