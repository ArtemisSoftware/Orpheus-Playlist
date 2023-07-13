package com.artemissoftware.orpheusplaylist.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.player.composables.PlayerButtons
import com.artemissoftware.orpheusplaylist.player.composables.SongDescription
import com.artemissoftware.orpheusplaylist.player.composables.TopAppBar
import com.artemissoftware.orpheusplaylist.ui.theme.BackgroundOne
import com.artemissoftware.orpheusplaylist.ui.theme.BackgroundThree
import com.artemissoftware.orpheusplaylist.ui.theme.BackgroundTwo
import java.time.Duration

@Composable
fun PlayerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BackgroundOne,
                        BackgroundTwo,
                        BackgroundThree,
                    ),
                ),
            )
            .padding(horizontal = 10.dp),
    ) {
        TopAppBar()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 10.dp),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(30.dp))
//            Image(
//                modifier = Modifier.size(70.dp).weight(0.5f),
//                bitmap = audio.albumArt.asImageBitmap(),
//                contentDescription = "Cover Photo",
//            )
            Image(
                painter = painterResource(id = R.drawable.musics),
                contentDescription = "Song banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .sizeIn(maxWidth = 500.dp, maxHeight = 500.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .weight(10f),
            )
            Spacer(modifier = Modifier.height(30.dp))
            SongDescription("Title Songs", "Singer Name")
            Spacer(modifier = Modifier.height(35.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(10f),
            ) {
                PlayerSlider(Duration.ofHours(2))
                Spacer(modifier = Modifier.height(40.dp))
                PlayerButtons(modifier = Modifier.padding(vertical = 8.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerScreenPreview() {
    PlayerScreen()
}
