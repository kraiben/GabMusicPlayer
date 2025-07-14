package com.gab.gabsmusicplayer.data.repository.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.gab.gabsmusicplayer.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {

    override fun getMinDurationInSeconds(): Flow<Long> = dataStore.data
        .map {
            it[MIN_DURATION_KEY] ?: 90L
        }

    override suspend fun setMinDurationInSeconds(duration: Long) {
        dataStore.edit { settings ->
            settings[MIN_DURATION_KEY] = duration
        }
    }

    override fun isThemeDark(): Flow<Boolean> = dataStore.data
        .map {
            it[IS_THEME_DARK_KEY] ?: true
        }

    override suspend fun isDarkThemeChange() {
        dataStore.edit {
            val curValue = it[IS_THEME_DARK_KEY] ?: true
            it[IS_THEME_DARK_KEY] = !curValue
        }
    }

    companion object {
        private val MIN_DURATION_KEY = longPreferencesKey("min_duration")
        private val IS_THEME_DARK_KEY = booleanPreferencesKey("is_theme_dark")
    }
}