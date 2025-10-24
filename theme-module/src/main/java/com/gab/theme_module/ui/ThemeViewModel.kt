package com.gab.theme_module.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gab.model_module.usecases.IsDarkThemeChangeUseCase
import com.gab.model_module.usecases.IsDarkThemeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

public class ThemeViewModel @Inject constructor(
    private val isDarkThemeUseCase: IsDarkThemeUseCase,
    private val isDarkThemeChangeUseCase: IsDarkThemeChangeUseCase
) : ViewModel(){

    internal val isThemeDark = isDarkThemeUseCase()
    internal fun isDarkThemeChange() {
        viewModelScope.launch {
            isDarkThemeChangeUseCase()
        }
    }
}