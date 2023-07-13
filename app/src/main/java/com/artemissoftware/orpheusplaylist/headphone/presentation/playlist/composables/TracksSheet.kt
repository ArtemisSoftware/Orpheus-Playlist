package com.artemissoftware.orpheusplaylist.headphone.presentation.playlist.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.R
import com.artemissoftware.orpheusplaylist.headphone.data.model.AudioMetadata
import com.artemissoftware.orpheusplaylist.headphone.presentation.activity.AudioPlayerState

@Composable
fun TracksSheet(
    state: com.artemissoftware.orpheusplaylist.headphone.presentation.activity.AudioPlayerState,
    onClick: (AudioMetadata) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .padding(top = 16.dp),
    ) {
        if (state.audios.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                WarningMessage(
                    text = stringResource(id = R.string.txt_no_media),
                    iconResId = R.drawable.circle_info_solid,
                    modifier = Modifier.padding(vertical = 16.dp),
                )
            }
        } else {
            TrackList(
                state = state,
                onClick = onClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TracksSheetPreview() {
    TracksSheet(com.artemissoftware.orpheusplaylist.headphone.presentation.activity.AudioPlayerState())
}
