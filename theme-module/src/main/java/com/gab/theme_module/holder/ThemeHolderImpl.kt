package com.gab.theme_module.holder

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gab.theme_api_module.ThemeHolder
import com.gab.theme_module.ui.GabsMusicPlayerTheme
import com.gab.theme_module.ui.ThemeViewModel
import javax.inject.Inject

public class ThemeHolderImpl @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): ThemeHolder {

    private var changeTheme: () -> Unit = {}

    @Composable
    override fun Theme(content: @Composable () -> Unit) {
        val viewModel = viewModel<ThemeViewModel>(factory = viewModelFactory)
        changeTheme = remember { { viewModel.isDarkThemeChange() } }
        val isThemeDark by viewModel.isThemeDark.collectAsState()
        GabsMusicPlayerTheme(isThemeDark = isThemeDark) {
            (LocalActivity.current as? ComponentActivity)?.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(
                    MaterialTheme.colorScheme.primaryContainer.toArgb()
                ),
                navigationBarStyle = SystemBarStyle.dark(
                    MaterialTheme.colorScheme.primaryContainer.toArgb()
                )
            )
            content()
        }
    }

    override fun themeSwitch() {
        changeTheme()
    }
}