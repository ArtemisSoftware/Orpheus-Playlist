package com.artemissoftware.orpheusplaylist.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MediaDescription(
    title: String,
    name: String,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 4.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = textColor,
            fontWeight = FontWeight.Bold,
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = name,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                color = textColor,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaDescriptionPreview() {
    MediaDescription(
        title = "title",
        name = "name",
        textColor = Color.Black,
    )
}
