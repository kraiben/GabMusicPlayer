package com.gab.gabsmusicplayer.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getMinDurationInSeconds(): Flow<Long>
    suspend fun setMinDurationInSeconds(duration: Long)

    fun isThemeDark(): Flow<Boolean>
    suspend fun isDarkThemeChange()

}