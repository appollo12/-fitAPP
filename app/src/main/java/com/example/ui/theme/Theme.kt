package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = TerracottaPrimary,
    onPrimary = Color.White,
    primaryContainer = TerracottaContainer,
    onPrimaryContainer = TerracottaPrimary,
    secondary = TerracottaSecondary,
    onSecondary = Color.White,
    background = CreamBackground,
    onBackground = TextDarkCoffee,
    surface = CardSurface,
    onSurface = TextDarkCoffee,
    surfaceVariant = SoftCreamSurface,
    onSurfaceVariant = TextMutedSand,
    outline = CardStroke
)

private val DarkColorScheme = darkColorScheme(
    primary = TerracottaLight,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3A1A10),
    onPrimaryContainer = Color(0xFFFFDBCD),
    secondary = TerracottaSecondary,
    onSecondary = Color.White,
    background = Color(0xFF1E1815),
    onBackground = Color(0xFFF3EEE7),
    surface = Color(0xFF2A221E),
    onSurface = Color(0xFFF3EEE7),
    surfaceVariant = Color(0xFF352C27),
    onSurfaceVariant = Color(0xFFC0B4AC),
    outline = Color(0xFF483E38)
)

@Composable
fun YegnaFitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
