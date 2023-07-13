package com.artemissoftware.orpheusplaylist.headphone.presentation.playlist

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.headphone.presentation.activity.AudioPlayerState
import com.artemissoftware.orpheusplaylist.headphone.presentation.playlist.composables.LoadingDialog
import com.artemissoftware.orpheusplaylist.headphone.presentation.playlist.composables.SongPage
import com.artemissoftware.orpheusplaylist.headphone.presentation.playlist.composables.TracksSheet
import com.artemissoftware.orpheusplaylist.headphone.util.audio.VisualizerData
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayListScreen(
    state: com.artemissoftware.orpheusplaylist.headphone.presentation.activity.AudioPlayerState,
    event: KFunction1<AudioPlayerEvent, Unit>,
    requestPermissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    visualizerData: State<VisualizerData>,
) {
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded,
        skipHalfExpanded = true,
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
                                },
                            ),
                        )
                    }
                },
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
                },
            )

            SongPage(
                state = state,
                event = event,
                context = context,
                sheetState = sheetState,
                scope = scope,
                visualizerData = visualizerData,
                requestPermissionLauncher = requestPermissionLauncher,
            )
        },
    )
}
