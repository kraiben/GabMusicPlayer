package com.gab.navigation_module.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gab.model_module.usecases.DecrementDurationUseCase
import com.gab.model_module.usecases.GetMinDurationInSecondsUseCase
import com.gab.model_module.usecases.IncrementDurationUseCase
import com.gab.model_module.usecases.IsDarkThemeChangeUseCase
import com.gab.model_module.usecases.IsDarkThemeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

public class NavigationViewModel @Inject constructor(
    private val getMinDurationInSecondsUseCase: GetMinDurationInSecondsUseCase,
    private val incrementDurationUseCase: IncrementDurationUseCase,
    private val decrementDurationUseCase: DecrementDurationUseCase,
    private val isDarkThemeUseCase: IsDarkThemeUseCase,
    private val isDarkThemeChangeUseCase: IsDarkThemeChangeUseCase,
): ViewModel() {

    private val minDuration = getMinDurationInSecondsUseCase()
    private val isDarkThemeSet = isDarkThemeUseCase()
    internal fun getMinDuration() = minDuration
    internal fun getIsDarkThemeSet() = isDarkThemeSet

    internal fun incrementDuration() {
        viewModelScope.launch {
            incrementDurationUseCase()
        }
    }
    internal fun decrementDuration() {
        viewModelScope.launch {
            decrementDurationUseCase()
        }
    }
    internal fun isDarkThemeChange() {
        viewModelScope.launch {
            isDarkThemeChangeUseCase()
        }
    }
}