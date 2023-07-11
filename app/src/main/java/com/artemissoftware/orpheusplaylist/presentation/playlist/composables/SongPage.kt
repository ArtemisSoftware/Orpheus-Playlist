package com.artemissoftware.orpheusplaylist.presentation.playlist.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.presentation.activity.AudioPlayerState
import com.artemissoftware.orpheusplaylist.presentation.playlist.AudioPlayerEvent

@Composable
fun SongPage(
    state: AudioPlayerState,
    event: (AudioPlayerEvent) -> Unit,
) {

    val screenHeight = screenHeight()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .background(color = MaterialTheme.colors.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBar(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .requiredHeight(height = 80.dp),
                    leadingIcon = {
                        LikeButton(
                            isLiked = state.likedSongs.contains(state.selectedAudio.songId),
                            enabled = state.selectedAudio.isNotEmpty(),
                            onClick = {
//                                mainViewModel.onEvent(
//                                    event = AudioPlayerEvent.LikeOrNotSong(
//                                        id = state.selectedAudio.songId
//                                    )
//                                )
                            }
                        )
                    },
                    title = {
                        if (state.selectedAudio.isNotEmpty()) {
                            val artist = if (state.selectedAudio.artist.contains(
                                    "unknown",
                                    ignoreCase = true
                                )
                            ) "" else "${state.selectedAudio.artist} - "
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(text = artist)
                                    }
                                    append(text = "  ${state.selectedAudio.songTitle}")
                                },
                                color = MaterialTheme.colors.onSurface,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.h3
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
//                            setupPermissions(
//                                context = context,
//                                permissions = arrayOf(
//                                    Manifest.permission.RECORD_AUDIO,
//                                    Manifest.permission.READ_EXTERNAL_STORAGE
//                                ),
//                                launcher = requestPermissionLauncher,
//                                onPermissionsGranted = {
//                                    scope.launch {
//                                        if (state.audios.isEmpty()) {
//                                            mainViewModel.onEvent(event = AudioPlayerEvent.LoadMedias)
//                                        }
//                                        sheetState.show()
//                                    }
//                                }
//                            )
                        },
                            content = {
                            Icon(
                                painter = painterResource(id = R.drawable.up_right_from_square_solid),
                                contentDescription = "",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                        )
                    }
                )

                Spacer(modifier = Modifier.requiredHeight(height = 16.dp))

                state.selectedAudio.cover?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        modifier = Modifier
                            .requiredHeight(height = screenHeight * 0.4f)
                            .clip(shape = MaterialTheme.shapes.large),
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )
                } ?: Box(
                    modifier = Modifier.requiredHeight(height = screenHeight * 0.4f),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        elevation = 8.dp,
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.fillMaxHeight(fraction = 0.5f)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.musical_note_music_svgrepo_com),
                            modifier = Modifier
                                .padding(
                                    top = 25.dp,
                                    bottom = 26.dp,
                                    start = 8.dp,
                                    end = 20.dp
                                ),
                            contentScale = ContentScale.FillHeight,
                            contentDescription = ""
                        )
                    }
                }

                Spacer(modifier = Modifier.requiredHeight(height = 16.dp))

//                StackedBarVisualizer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(height = 200.dp)
//                        .padding(vertical = 4.dp, horizontal = 8.dp),
//                    shape = MaterialTheme.shapes.large,
//                    barCount = 32,
//                    barColors = listOf(
//                        Color(0xFF1BEBE9),
//                        Color(0xFF39AFEA),
//                        Color(0xFF0291D8)
//                    ),
//                    stackBarBackgroundColor = if (isSystemInDarkTheme()) Black3 else
//                        MaterialTheme.colors.onSurface.copy(alpha = 0.25f),
//                    data = mainViewModel.visualizerData.value
//                )

                Spacer(modifier = Modifier.requiredHeight(height = 10.dp))

//                TimeBar(
//                    currentPosition = state.currentPosition,
//                    onValueChange = { position ->
//                        mainViewModel.onEvent(event = AudioPlayerEvent.Seek(position = position))
//                    },
//                    duration = state.selectedAudio.duration
//                )

                Spacer(modifier = Modifier.requiredHeight(height = 10.dp))
/*
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FastButton(
                        enabled = state.currentPosition > FORWARD_BACKWARD_STEP,
                        onClick = {
                            mainViewModel.onEvent(
                                event = AudioPlayerEvent.Seek(position = state.currentPosition - FORWARD_BACKWARD_STEP.toFloat())
                            )
                        },
                        iconResId = R.drawable.backward_solid,
                        stringResId = R.string.lbl_fast_backward
                    )
                    PlayPauseButton(
                        modifier = Modifier.padding(horizontal = 26.dp),
                        enabled = state.selectedAudio.isNotEmpty(),
                        isPlaying = state.isPlaying,
                        onPlay = { mainViewModel.onEvent(event = AudioPlayerEvent.Play) },
                        onPause = { mainViewModel.onEvent(event = AudioPlayerEvent.Pause) }
                    )
                    FastButton(
                        enabled = state.currentPosition < (state.selectedAudio.duration - FORWARD_BACKWARD_STEP),
                        onClick = {
                            mainViewModel.onEvent(
                                event = AudioPlayerEvent.Seek(position = state.currentPosition + FORWARD_BACKWARD_STEP.toFloat())
                            )
                        },
                        iconResId = R.drawable.forward_solid,
                        stringResId = R.string.lbl_fast_forward
                    )
                }
                */
            }

}

@Preview(showBackground = true)
@Composable
private fun SongPagePreview() {
//    SongPage()
}