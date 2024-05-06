package com.github.mrzahmadi.lightnote.ui.theme

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.github.mrzahmadi.lightnote.data.repository.SharedPreferencesRepository


private val DarkColorScheme = darkColorScheme(
    primary = PrimaryNight,
    secondary = SecondaryNight,
    tertiary = TertiaryNight,
    background = BackgroundNight
)

val LightColorScheme = lightColorScheme(
    primary = PrimaryDay,
    secondary = SecondaryDay,
    tertiary = TertiaryDay,
    background = BackgroundDay

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
fun LightNoteTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme)
        DarkColorScheme
    else
        LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


fun isSettingsOnNightMode(context: Context): Boolean {
    val spRepository = SharedPreferencesRepository(context)
    val theme = spRepository[SharedPreferencesRepository.THEME, DAY_MODE]
    return when (theme) {
        DAY_MODE -> false
        NIGHT_MODE -> true
        FOLLOW_SYSTEM -> isSystemOnNightMode(context)
        else -> false
    }
}

private fun isSystemOnNightMode(context: Context): Boolean {
    val nightModeFlags = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
}

const val DAY_MODE = 0
const val NIGHT_MODE = 1
const val FOLLOW_SYSTEM = 2