package com.artemissoftware.orpheusplaylist.headphone.presentation.playlist.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.headphone.presentation.activity.AudioPlayerState
import com.artemissoftware.orpheusplaylist.headphone.presentation.playlist.AudioPlayerEvent
import kotlin.reflect.KFunction1

@Composable
fun PlaylistContent(
    state: com.artemissoftware.orpheusplaylist.headphone.presentation.activity.AudioPlayerState,
    screenHeight: Dp,
    event: KFunction1<AudioPlayerEvent, Unit>
) {

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
                        event(
                             AudioPlayerEvent.LikeOrNotSong(
                                id = state.selectedAudio.songId
                            )
                        )
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
//                IconButton(onClick = {
//                    setupPermissions(
//                        context = context,
//                        permissions = arrayOf(
//                            Manifest.permission.RECORD_AUDIO,
//                            Manifest.permission.READ_EXTERNAL_STORAGE
//                        ),
//                        launcher = requestPermissionLauncher,
//                        onPermissionsGranted = {
//                            scope.launch {
//                                if (state.audios.isEmpty()) {
//                                    mainViewModel.onEvent(event = AudioPlayerEvent.LoadMedias)
//                                }
//                                sheetState.show()
//                            }
//                        }
//                    )
//                }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.up_right_from_square_solid),
//                        contentDescription = "",
//                        tint = MaterialTheme.colors.onSurface
//                    )
//                }
            }
        )

        Spacer(modifier = Modifier.requiredHeight(height = 16.dp))

        AudioCover(screenHeight = screenHeight, bitmap = state.selectedAudio.cover)

        Spacer(modifier = Modifier.requiredHeight(height = 16.dp))

//        StackedBarVisualizer(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(height = 200.dp)
//                .padding(vertical = 4.dp, horizontal = 8.dp),
//            shape = MaterialTheme.shapes.large,
//            barCount = 32,
//            barColors = listOf(
//                Color(0xFF1BEBE9),
//                Color(0xFF39AFEA),
//                Color(0xFF0291D8)
//            ),
//            stackBarBackgroundColor = if (isSystemInDarkTheme()) Black3 else
//                MaterialTheme.colors.onSurface.copy(alpha = 0.25f),
//            data = mainViewModel.visualizerData.value
//        )

        Spacer(modifier = Modifier.requiredHeight(height = 10.dp))

//        TimeBar(
//            currentPosition = state.currentPosition,
//            onValueChange = { position ->
//                mainViewModel.onEvent(event = AudioPlayerEvent.Seek(position = position))
//            },
//            duration = state.selectedAudio.duration
//        )

        Spacer(modifier = Modifier.requiredHeight(height = 10.dp))

//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(all = 10.dp),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            FastButton(
//                enabled = state.currentPosition > FORWARD_BACKWARD_STEP,
//                onClick = {
//                    mainViewModel.onEvent(
//                        event = AudioPlayerEvent.Seek(position = state.currentPosition - FORWARD_BACKWARD_STEP.toFloat())
//                    )
//                },
//                iconResId = R.drawable.backward_solid,
//                stringResId = R.string.lbl_fast_backward
//            )
//            PlayPauseButton(
//                modifier = Modifier.padding(horizontal = 26.dp),
//                enabled = state.selectedAudio.isNotEmpty(),
//                isPlaying = state.isPlaying,
//                onPlay = { mainViewModel.onEvent(event = AudioPlayerEvent.Play) },
//                onPause = { mainViewModel.onEvent(event = AudioPlayerEvent.Pause) }
//            )
//            FastButton(
//                enabled = state.currentPosition < (state.selectedAudio.duration - FORWARD_BACKWARD_STEP),
//                onClick = {
//                    mainViewModel.onEvent(
//                        event = AudioPlayerEvent.Seek(position = state.currentPosition + FORWARD_BACKWARD_STEP.toFloat())
//                    )
//                },
//                iconResId = R.drawable.forward_solid,
//                stringResId = R.string.lbl_fast_forward
//            )
//        }
    }
}