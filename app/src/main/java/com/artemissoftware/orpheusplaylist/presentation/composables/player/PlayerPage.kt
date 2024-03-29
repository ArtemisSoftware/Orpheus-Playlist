package com.artemissoftware.orpheusplaylist.presentation.composables.player

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.DummyData
import com.artemissoftware.orpheusplaylist.OrpheusPlaylistState
import com.artemissoftware.orpheusplaylist.data.models.AlbumType
import com.artemissoftware.orpheusplaylist.domain.models.Audio
import com.artemissoftware.orpheusplaylist.domain.models.visualizer.VisualizerData
import com.artemissoftware.orpheusplaylist.presentation.composables.MediaDescription
import com.artemissoftware.orpheusplaylist.presentation.composables.StackedBarVisualizer
import com.artemissoftware.orpheusplaylist.presentation.composables.album.AlbumCoverCarousel
import com.artemissoftware.orpheusplaylist.ui.theme.VisualizerBarGradient

@Composable
fun PlayerPage(
    playerState: OrpheusPlaylistState,
    isAudioPlaying: Boolean,
    visualizerData: VisualizerData,
    onProgressChange: (Float) -> Unit,
    onCollapse: () -> Unit,
    onPlayTrack: () -> Unit,
    onSwipePlay: (Audio) -> Unit,
    onSkipToNext: () -> Unit,
    onSkipToPrevious: () -> Unit,
    onUpdateUserPlaylist: (Long) -> Unit,
    onUpdateAudioIndex: (Audio) -> Unit,
    modifier: Modifier = Modifier,
    currentPlaying: Audio? = null,
) {
    BackHandler(enabled = playerState.fullPlayer) {
        onCollapse.invoke()
    }

    LaunchedEffect(key1 = currentPlaying) {
        if (currentPlaying != null) onUpdateAudioIndex.invoke(currentPlaying)
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1F),
        ) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.75F),
            verticalAlignment = Alignment.CenterVertically,
        ) {
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
                            title = track.title,
                            name = track.artist,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.15F),
            verticalAlignment = Alignment.Bottom,
        ) {
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
                    barColors = VisualizerBarGradient,
                    stackBarBackgroundColor = Color.Transparent,
                    data = visualizerData,
                )
            }
        }
    }
}

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
        onUpdateAudioIndex = {},
        modifier = Modifier
            .fillMaxSize(),
        currentPlaying = DummyData.audio,
    )
}
