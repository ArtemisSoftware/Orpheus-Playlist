package com.artemissoftware.orpheusplaylist.presentation.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.presentation.activity.AudioPlayerState
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.LoadingDialog
import com.artemissoftware.orpheusplaylist.presentation.playlist.composables.TracksSheet
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayListScreen(
    state: AudioPlayerState,
    event: KFunction1<AudioPlayerEvent, Unit>
) {
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val scope = rememberCoroutineScope()


    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = MaterialTheme.shapes.large,
        sheetContent = {

            TracksSheet(
                state = state,
                onClick = {
                    scope.launch {
                        event(AudioPlayerEvent.Stop)
                        sheetState.hide()
                        event(
                            AudioPlayerEvent.InitAudio(
                                audio = it,
                                context = context,
                                onAudioInitialized = {
                                    event(AudioPlayerEvent.Play)
                                }
                            )
                        )
                    }
                }
            )
        },
        content = {
            LoadingDialog(
                isLoading = state.isLoading,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.large)
                    .background(color = MaterialTheme.colors.surface)
                    .requiredSize(size = 80.dp),
                onDone = {
                    event(AudioPlayerEvent.HideLoadingDialog)
                }
            )
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .verticalScroll(state = rememberScrollState())
//                    .background(color = MaterialTheme.colors.background),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                TopBar(
//                    modifier = Modifier
//                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
//                        .requiredHeight(height = 80.dp),
//                    leadingIcon = {
//                        LikeButton(
//                            isLiked = state.likedSongs.contains(state.selectedAudio.songId),
//                            enabled = state.selectedAudio.isNotEmpty(),
//                            onClick = {
//                                mainViewModel.onEvent(
//                                    event = AudioPlayerEvent.LikeOrNotSong(
//                                        id = state.selectedAudio.songId
//                                    )
//                                )
//                            }
//                        )
//                    },
//                    title = {
//                        if (state.selectedAudio.isNotEmpty()) {
//                            val artist = if (state.selectedAudio.artist.contains(
//                                    "unknown",
//                                    ignoreCase = true
//                                )
//                            ) "" else "${state.selectedAudio.artist} - "
//                            Text(
//                                text = buildAnnotatedString {
//                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
//                                        append(text = artist)
//                                    }
//                                    append(text = "  ${state.selectedAudio.songTitle}")
//                                },
//                                color = MaterialTheme.colors.onSurface,
//                                overflow = TextOverflow.Ellipsis,
//                                style = MaterialTheme.typography.h3
//                            )
//                        }
//                    },
//                    trailingIcon = {
//                        IconButton(onClick = {
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
//                        }) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.up_right_from_square_solid),
//                                contentDescription = "",
//                                tint = MaterialTheme.colors.onSurface
//                            )
//                        }
//                    }
//                )
//
//                Spacer(modifier = Modifier.requiredHeight(height = 16.dp))
//
//                state.selectedAudio.cover?.let {
//                    Image(
//                        bitmap = it.asImageBitmap(),
//                        modifier = Modifier
//                            .requiredHeight(height = screenHeight * 0.4f)
//                            .clip(shape = MaterialTheme.shapes.large),
//                        contentScale = ContentScale.Crop,
//                        contentDescription = ""
//                    )
//                } ?: Box(
//                    modifier = Modifier.requiredHeight(height = screenHeight * 0.4f),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Card(
//                        elevation = 8.dp,
//                        shape = MaterialTheme.shapes.large,
//                        modifier = Modifier.fillMaxHeight(fraction = 0.5f)
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.musical_note_music_svgrepo_com),
//                            modifier = Modifier
//                                .padding(
//                                    top = 25.dp,
//                                    bottom = 26.dp,
//                                    start = 8.dp,
//                                    end = 20.dp
//                                ),
//                            contentScale = ContentScale.FillHeight,
//                            contentDescription = ""
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.requiredHeight(height = 16.dp))
//
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
//
//                Spacer(modifier = Modifier.requiredHeight(height = 10.dp))
//
//                TimeBar(
//                    currentPosition = state.currentPosition,
//                    onValueChange = { position ->
//                        mainViewModel.onEvent(event = AudioPlayerEvent.Seek(position = position))
//                    },
//                    duration = state.selectedAudio.duration
//                )
//
//                Spacer(modifier = Modifier.requiredHeight(height = 10.dp))
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(all = 10.dp),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    FastButton(
//                        enabled = state.currentPosition > FORWARD_BACKWARD_STEP,
//                        onClick = {
//                            mainViewModel.onEvent(
//                                event = AudioPlayerEvent.Seek(position = state.currentPosition - FORWARD_BACKWARD_STEP.toFloat())
//                            )
//                        },
//                        iconResId = R.drawable.backward_solid,
//                        stringResId = R.string.lbl_fast_backward
//                    )
//                    PlayPauseButton(
//                        modifier = Modifier.padding(horizontal = 26.dp),
//                        enabled = state.selectedAudio.isNotEmpty(),
//                        isPlaying = state.isPlaying,
//                        onPlay = { mainViewModel.onEvent(event = AudioPlayerEvent.Play) },
//                        onPause = { mainViewModel.onEvent(event = AudioPlayerEvent.Pause) }
//                    )
//                    FastButton(
//                        enabled = state.currentPosition < (state.selectedAudio.duration - FORWARD_BACKWARD_STEP),
//                        onClick = {
//                            mainViewModel.onEvent(
//                                event = AudioPlayerEvent.Seek(position = state.currentPosition + FORWARD_BACKWARD_STEP.toFloat())
//                            )
//                        },
//                        iconResId = R.drawable.forward_solid,
//                        stringResId = R.string.lbl_fast_forward
//                    )
//                }
//            }
        }
    )

}