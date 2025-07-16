package com.gab.gabsmusicplayer.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {

    fun getMinDurationInSeconds(): StateFlow<Long>
    suspend fun incrementMinDuration()
    suspend fun decrementMinDuration()

    fun isThemeDark(): StateFlow<Boolean>
    suspend fun isDarkThemeChange()

}