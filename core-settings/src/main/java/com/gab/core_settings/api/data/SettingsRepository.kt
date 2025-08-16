package com.gab.core_settings.api.data

import kotlinx.coroutines.flow.StateFlow

public interface SettingsRepository {

    public fun getMinDurationInSeconds(): StateFlow<Long>
    public suspend fun incrementMinDuration()
    public suspend fun decrementMinDuration()

    public fun isThemeDark(): StateFlow<Boolean>
    public suspend fun isDarkThemeChange()

}