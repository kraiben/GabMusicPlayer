package com.gab.theme_api_module

import androidx.compose.runtime.Composable

public interface ThemeHolder {

    @Composable
    public fun Theme(content: @Composable () -> Unit)

    public fun themeSwitch()
}