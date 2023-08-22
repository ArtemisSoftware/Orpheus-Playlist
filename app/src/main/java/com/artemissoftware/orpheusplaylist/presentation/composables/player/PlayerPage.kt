package com.artemissoftware.orpheusplaylist.presentation.composables.player

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistState
import com.artemissoftware.orpheusplaylist.data.models.AlbumType
import com.artemissoftware.orpheusplaylist.data.models.AudioMetadata
import com.artemissoftware.orpheusplaylist.headphone.presentation.playlist.composables.StackedBarVisualizer
import com.artemissoftware.orpheusplaylist.headphone.util.audio.VisualizerData
import com.artemissoftware.orpheusplaylist.presentation.composables.MediaDescription
import com.artemissoftware.orpheusplaylist.ui.theme.Black3

@Composable
fun PlayerPage(
    playerState: OrpheusPlaylistState,
    isAudioPlaying: Boolean,
    visualizerData: VisualizerData,
    onProgressChange: (Float) -> Unit,
    onCollapse: () -> Unit,
    onPlayTrack: () -> Unit,
    onSwipePlay: (AudioMetadata) -> Unit,
    onSkipToNext: () -> Unit,
    onSkipToPrevious: () -> Unit,
    onUpdateUserPlaylist: (Long) -> Unit,
    modifier: Modifier = Modifier,
    currentPlaying: AudioMetadata? = null,
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth().weight(0.1F).background(color = Color.Green)) {
            PlayerTopBar(
                isOnUserPlayList = currentPlaying?.let { playerState.favorites.contains(it.id) } ?: false,
                onCollapse = onCollapse,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                tint = Color.White,
                showAddToPlayList = playerState.loadedAlbum?.type == AlbumType.ALBUM,
                onUpdateUserPlaylist = {
                    currentPlaying?.let { onUpdateUserPlaylist.invoke(it.id) }
                },
            )
        }
        Row(modifier = Modifier.fillMaxWidth().weight(0.75F).background(color = Color.Red), verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                playerState.loadedAlbum?.let { album ->

                    AlbumCoverCarousel(
                        modifier = Modifier.fillMaxWidth(),
                        tracks = album.tracks,
                        index = playerState.currentAudioIndex,
                        onSwipePlay = {
                            if (album.tracks.isNotEmpty()) onSwipePlay.invoke(album.tracks[it])
                        },
                    )

                    currentPlaying?.let { track ->
                        MediaDescription(
                            textColor = Color.White,
                            title = track.name,
                            name = track.albumMetadata.artist.name,
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        )

                        PlayerControllerDisplay(
                            progress = playerState.currentAudioProgress,
                            playBackPosition = playerState.currentPlayBackPosition,
                            onProgressChange = onProgressChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            duration = track.timeStampToDuration(),
                            isAudioPlaying = isAudioPlaying,
                            onPlay = {
                                onPlayTrack.invoke()
                            },
                            onPrevious = onSkipToPrevious,
                            onNext = onSkipToNext,
                            displayColor = Color.White,
                        )
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().weight(0.15F).background(color = Color.Yellow), verticalAlignment = Alignment.Bottom) {
            AnimatedVisibility(
                modifier = Modifier,
                visible = isAudioPlaying,
                enter = fadeIn(animationSpec = tween(800)),
                exit = fadeOut(animationSpec = tween(400)) + slideOutVertically(tween(500), targetOffsetY = {
                    it / 2
                }),
            ) {
                StackedBarVisualizer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .alpha(0.5F),
                    shape = MaterialTheme.shapes.large,
                    barCount = 32,
                    barColors = listOf(
                        Color(0xFF1BEBE9),
                        Color(0xFF39AFEA),
                        Color(0xFF0291D8),
                    ),
                    stackBarBackgroundColor = if (isSystemInDarkTheme()) {
                        Black3
                    } else {
                        Color.Transparent
                    },
                    data = visualizerData,
                )
            }
        }
    }
}
/*
@Composable
fun PlayerPage_(
    playerState: OrpheusPlaylistState,
    isAudioPlaying: Boolean,
    visualizerData: VisualizerData,
    onProgressChange: (Float) -> Unit,
    onCollapse: () -> Unit,
    onPlayTrack: () -> Unit,
    onSwipePlay: (AudioMetadata) -> Unit,
    onSkipToNext: () -> Unit,
    onSkipToPrevious: () -> Unit,
    onUpdateUserPlaylist: (Long) -> Unit,
    modifier: Modifier = Modifier,
    currentPlaying: AudioMetadata? = null,
) {
    BackHandler(enabled = playerState.fullPlayer) {
        onCollapse.invoke()
    }

    Box(
        modifier = modifier,
    ) {
        PlayerTopBar(
            isOnUserPlayList = currentPlaying?.let { playerState.favorites.contains(it.id) } ?: false,
            onCollapse = onCollapse,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 16.dp),
            tint = Color.White,
            showAddToPlayList = playerState.loadedAlbum?.type == AlbumType.ALBUM,
            onUpdateUserPlaylist = {
                currentPlaying?.let { onUpdateUserPlaylist.invoke(it.id) }
            },
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            playerState.loadedAlbum?.let { album ->

                AlbumCoverCarousel(
                    modifier = Modifier.fillMaxWidth(),
                    tracks = album.tracks,
                    index = playerState.currentAudioIndex,
                    onSwipePlay = {
                        if (album.tracks.isNotEmpty()) onSwipePlay.invoke(album.tracks[it])
                    },
                )

                currentPlaying?.let { track ->
                    MediaDescription(
                        textColor = Color.White,
                        title = track.name,
                        name = track.albumMetadata.artist.name,
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    )

                    PlayerControllerDisplay(
                        progress = playerState.currentAudioProgress,
                        playBackPosition = playerState.currentPlayBackPosition,
                        onProgressChange = onProgressChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        duration = track.timeStampToDuration(),
                        isAudioPlaying = isAudioPlaying,
                        onPlay = {
                            onPlayTrack.invoke()
                        },
                        onPrevious = onSkipToPrevious,
                        onNext = onSkipToNext,
                        displayColor = Color.White,
                    )
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = isAudioPlaying,
            enter = fadeIn(animationSpec = tween(800)),
            exit = fadeOut(animationSpec = tween(400)) + slideOutVertically(tween(500), targetOffsetY = {
                it / 2
            }),
        ) {
            StackedBarVisualizer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 100.dp)
                    .alpha(0.5F)
                    .align(Alignment.BottomCenter),
                shape = MaterialTheme.shapes.large,
                barCount = 32,
                barColors = listOf(
                    Color(0xFF1BEBE9),
                    Color(0xFF39AFEA),
                    Color(0xFF0291D8),
                ),
                stackBarBackgroundColor = if (isSystemInDarkTheme()) {
                    Black3
                } else {
                    Color.Transparent
                },
                data = visualizerData,
            )
        }
    }
}
*/
@Preview(showBackground = true)
@Composable
private fun PlayerPagePreview() {
    PlayerPage(
        playerState = OrpheusPlaylistState(loadedAlbum = DummyData.album, currentAudioIndex = 0),
        isAudioPlaying = false,
        visualizerData = VisualizerData(),
        onProgressChange = {},
        onCollapse = {},
        onPlayTrack = {},
        onSwipePlay = {},
        onSkipToNext = {},
        onSkipToPrevious = {},
        onUpdateUserPlaylist = {},
        modifier = Modifier
            .fillMaxSize(),
        currentPlaying = DummyData.audioMetadata,
    )
}
