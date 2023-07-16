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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.presentation.playlist.lolo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.carouselTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

        val transformation = lerp(
            start = 0.8f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f),
        )
        alpha = transformation
        scaleY = transformation
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumCoverCarousel(
    mediaList: List<String>,
) {
    val ddd = remember {
        mutableStateOf(0)
    }

    val pagerState: PagerState = rememberPagerState()

    LaunchedEffect(key1 = pagerState.currentPage) {
        ddd.value = pagerState.currentPage
        delay(1000L)

        if(ddd.value == pagerState.currentPage){
            ddd.value = 1000
        }
    }

    HorizontalPager(
        state = pagerState,
        pageCount = mediaList.size,
        contentPadding = PaddingValues(
            horizontal = 60.dp,
        ),
        pageSpacing = 12.dp,
        modifier = Modifier.fillMaxWidth(),
    ) { page: Int ->

        Card(
            modifier = Modifier
                .carouselTransition(page, pagerState),
        ) {
            Text(
                text = page.toString() + ddd.value,
            )
            AlbumCover(item = mediaList[page])
        }
    }
}

@Composable
private fun AlbumCover(item: String) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(null)
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
            listOf(
                "AAAA",
                "BBBB",
                "CCCCC",
                "DDDDD",
                "EEEEE",
                "FFFFF",
                "GGGGG",
                "DDDDD",
                "BBBBB",
                "VVVV",
            ),
        )
    }
}
