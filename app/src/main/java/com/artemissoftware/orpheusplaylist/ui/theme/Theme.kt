package com.artemissoftware.orpheusplaylist.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/*
@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = Teal200,
    onPrimary = White,
    primaryVariant = TealA700,
    secondary = Teal200,
    error = RedErrorLight,
    background = Black2,
    onBackground = Gray300,
    surface = Black3,
    onSurface = Gray200,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = Teal200,
    onPrimary = White,
    primaryVariant = TealA700,
    secondary = Teal200,
    onSecondary = Black1,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = BackGroundColor,
    onBackground = onBackGround,
    surface = SurfaceColor,
    onSurface = onSurface,
)
*/

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Orange200,
    primaryVariant = Blue700,
    secondary = Orange200,
    background = Blue700,
    surface = Blue700/*Blue500*/,
    onSurface = Color.White,
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun OrpheusPlaylistTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = NunitoTypography,
        shapes = Shapes,
        content = content
    )
}