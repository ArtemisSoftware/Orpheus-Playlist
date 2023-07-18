package com.artemissoftware.orpheusplaylist.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.artemissoftware.orpheusplaylist.data.models.AlbumMetadata

@Composable
fun MediaCard(
    album: AlbumMetadata,
    onPlaylistClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(album.uri)
            .size(Size.ORIGINAL)
            .crossfade(800)
            .error(R.drawable.musical_note_music_svgrepo_com)
            .placeholder(R.drawable.musical_note_music_svgrepo_com)
            .build(),
    )

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable { onPlaylistClick(album.id) },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(space = 4.dp),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                elevation = 0.dp,
            ) {
                AsyncImage(
                    model = painter.request,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            MediaDescription(
                title = album.name,
                name = album.artist.name,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaCardPreview() {
    MediaCard(
        album = DummyData.albumMetadata,
        modifier = Modifier
            .fillMaxWidth(),
        onPlaylistClick = {},
    )
}
