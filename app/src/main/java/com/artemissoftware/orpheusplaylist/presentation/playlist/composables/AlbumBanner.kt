package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata

@Composable
fun AlbumBanner(
    album: AlbumMetadata?,
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(album?.uri)
            .size(Size.ORIGINAL)
            .crossfade(500)
            .error(R.drawable.musical_note_music_svgrepo_com)
            .placeholder(R.drawable.musical_note_music_svgrepo_com)
            .build(),
    )

    Surface(modifier = modifier) {
        AsyncImage(
            model = painter.request,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.4F)
                .blur(2.dp),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start,
            ),
            verticalAlignment = Alignment.Bottom,
            modifier = modifier.padding(12.dp),
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                elevation = 8.dp,
            ) {
                AsyncImage(
                    model = painter.request,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }

            album?.let {
                AlbumDescription(
                    albumName = it.name,
                    artist = it.artist.name,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}


@Composable
fun AlbumBanner(
    albumName: String,
    artistName: String,
    modifier: Modifier = Modifier,
    cover: Bitmap? = null,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(cover)
            .size(Size.ORIGINAL)
            .crossfade(500)
            .error(R.drawable.musical_note_music_svgrepo_com)
            .placeholder(R.drawable.musical_note_music_svgrepo_com)
            .build(),
    )

    Surface(modifier = modifier) {
        AsyncImage(
            model = painter.request,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.4F)
                .blur(2.dp),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start,
            ),
            verticalAlignment = Alignment.Bottom,
            modifier = modifier.padding(12.dp),
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                elevation = 8.dp,
            ) {
                AsyncImage(
                    model = painter.request,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }

            AlbumDescription(
                albumName = albumName,
                artist = artistName,
                modifier = Modifier.weight(1f),
            )

        }
    }
}


@Composable
private fun AlbumDescription(
    albumName: String,
    artist: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Bottom,
        ),
        horizontalAlignment = Alignment.End,
        modifier = modifier,
    ) {
        Text(
            text = albumName,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.End,
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = artist,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumBannerPreview() {
    AlbumBanner(album = DummyData.albumMetadata)
}

@Preview(showBackground = true)
@Composable
fun AlbumBanner_no_album_Preview() {
    AlbumBanner(album = null)
}
