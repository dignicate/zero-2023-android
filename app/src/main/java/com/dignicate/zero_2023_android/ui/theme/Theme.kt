package com.dignicate.zero_2023_android.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

/**
 * https://developer.android.com/jetpack/compose/designsystems/material3
 */

private val DarkColorScheme: ColorScheme = darkColorScheme(
    background = Color(0xFFEDF1F8),
    // primary = Purple80,
    // secondary = PurpleGrey80,
    // tertiary = Pink80
)

private val LightColorScheme: ColorScheme = lightColorScheme(
    background = Color(0xFFEDF1F8),
    // primary = Purple40,
    // secondary = PurpleGrey40,
    // tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Zero2023androidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        //     val context = LocalContext.current
        //     if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        // }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// FIXME: Need better way of extensions.
object ColorSchemeExtension {
    val ColorScheme.textMain by mutableStateOf(Color(0xFF11184B), structuralEqualityPolicy())
    val ColorScheme.textWhite by mutableStateOf(Color(0xFFFFFFFF), structuralEqualityPolicy())
    val ColorScheme.bookCell by mutableStateOf(Color(0xFFFFFFFF), structuralEqualityPolicy())
    val ColorScheme.bgGray by mutableStateOf(Color(0x70000000), structuralEqualityPolicy())
}
