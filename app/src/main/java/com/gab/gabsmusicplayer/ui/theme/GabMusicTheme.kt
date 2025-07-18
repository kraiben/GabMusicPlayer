package com.gab.gabsmusicplayer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private class Zametka(
    // Primary colors
    primary: Color,          // Основной брендовый цвет
    onPrimary: Color,        // Контент на primary
    primaryContainer: Color, // Контейнер для primary (например, фон кнопки)
    onPrimaryContainer: Color, // Контент на primaryContainer

    // Secondary colors
    secondary: Color,
    onSecondary: Color,
    secondaryContainer: Color,
    onSecondaryContainer: Color,

    // Tertiary colors (доп. акценты)
    tertiary: Color,
    onTertiary: Color,
    tertiaryContainer: Color,
    onTertiaryContainer: Color,

    // Background & Surface
    background: Color,       // Фон приложения
    onBackground: Color,     // Текст/иконки на фоне
    surface: Color,          // Цвет поверхностей (Card, Modal)
    onSurface: Color,        // Текст/иконки на surface
    surfaceVariant: Color,   // Вариант surface (например, Card в другом состоянии)
    onSurfaceVariant: Color, // Контент на surfaceVariant

    // Error colors
    error: Color,
    onError: Color,
    errorContainer: Color,
    onErrorContainer: Color,

    // Дополнительные
    outline: Color,          // Цвет границ (TextField, Divider)
    outlineVariant: Color,   // Вторичные границы
    scrim: Color,            // Затемнение (для ModalBottomSheet)
)

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

    background = Color.White,
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

