package com.gab.gabsmusicplayer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00E0FF),
    onPrimary = Color(0xFF00E0FF),
    primaryContainer = Color(0xFF006874),
    onPrimaryContainer = Color(0xFFAAF0FF),

    secondary = Color(0xFF4A635E),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),

    tertiary = Pink80,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color(0xFFFFD8E4),
    error = Color(0xFFFFB4AB),
    onError = Color.Black,
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    outline = Color(0xFF938F99)
)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00E0FF),
    onPrimary = Color(0xFF00E0FF),
    primaryContainer = Color(0xFF006874),
    onPrimaryContainer = Color(0xFFAAF0FF),

    secondary = Color(0xFF4A635E),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),

    tertiary = Pink80,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color(0xFFFFD8E4),

    error = Color(0xFFFFB4AB),
    onError = Color.Black,
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    background = Color(0xFFE6E1E5),
    onBackground = Color.Black,

    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    outline = Color(0xFF938F99)
)

@Composable
fun GabsMusicPlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

//@Composable
//fun MaterialTheme2(
//    colorScheme: ColorScheme = MaterialTheme.colorScheme,
//    shapes: Shapes = MaterialTheme.shapes,
//    typography: androidx.compose.material3.Typography = MaterialTheme.typography,
//    content: @Composable () -> Unit
//) {
//    val rippleIndication = rippleOrFallbackImplementation()
//    val selectionColors = rememberTextSelectionColors(colorScheme)
//    @Suppress("DEPRECATION_ERROR")
//    CompositionLocalProvider(
//        LocalColorScheme provides colorScheme,
//        LocalIndication provides rippleIndication,
//        // TODO: b/304985887 - remove after one stable release
//        androidx.compose.material.ripple.LocalRippleTheme provides CompatRippleTheme,
//        LocalShapes provides shapes,
//        LocalTextSelectionColors provides selectionColors,
//        LocalTypography provides typography,
//    ) {
//        ProvideTextStyle(value = typography.bodyLarge, content = content)
//    }
//}

//@Suppress("DEPRECATION_ERROR")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//internal fun rippleOrFallbackImplementation(
//    bounded: Boolean = true,
//    radius: Dp = Dp.Unspecified,
//    color: Color = Color.Unspecified
//): Indication {
//    return if (LocalUseFallbackRippleImplementation.current) {
//        androidx.compose.material.ripple.rememberRipple(bounded, radius, color)
//    } else {
//        ripple(bounded, radius, color)
//    }
//}
//@Composable
//internal fun rememberTextSelectionColors(colorScheme: ColorScheme): TextSelectionColors {
//    val primaryColor = colorScheme.primary
//    return remember(primaryColor) {
//        TextSelectionColors(
//            handleColor = primaryColor,
//            backgroundColor = primaryColor.copy(alpha = TextSelectionBackgroundOpacity),
//        )
//    }
//}
//const val TextSelectionBackgroundOpacity = 0.4f
//internal val LocalColorScheme = staticCompositionLocalOf { lightColorScheme() }
//internal val LocalShapes = staticCompositionLocalOf { Shapes() }
//internal val LocalTypography = staticCompositionLocalOf { Typography() }