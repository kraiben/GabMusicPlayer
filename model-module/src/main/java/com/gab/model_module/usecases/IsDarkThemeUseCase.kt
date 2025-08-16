package com.gab.model_module.usecases

import kotlinx.coroutines.flow.StateFlow

public interface IsDarkThemeUseCase {
    public operator fun invoke(): StateFlow<Boolean>
}