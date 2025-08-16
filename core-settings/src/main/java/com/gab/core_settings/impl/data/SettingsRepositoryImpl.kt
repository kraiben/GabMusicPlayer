package com.gab.core_settings.impl.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.gab.core_settings.api.data.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

public class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {


    private val cs = CoroutineScope(Dispatchers.IO)

    private val minDurationFlowUpdate =
        MutableSharedFlow<Long>(onBufferOverflow = BufferOverflow.DROP_OLDEST, replay = 1)

    private val minDurationInSecondsFlow = flow<Long> {
        minDurationFlowUpdate.emit(dataStore.data.map {
            it[MIN_DURATION_KEY] ?: 90L
        }.first())
        minDurationFlowUpdate.collect {
            val newValue = when {
                it < 0 -> 0
                it > 999 -> 990
                else -> it
            }
            dataStore.edit { settings ->
                settings[MIN_DURATION_KEY] = newValue
            }
            emit(newValue)
        }
    }.stateIn(cs, SharingStarted.Companion.Eagerly, 0L)

    override fun getMinDurationInSeconds(): StateFlow<Long> = minDurationInSecondsFlow

    private val minDurationFlowUpdateLocker = Mutex()
    override suspend fun incrementMinDuration() {
        minDurationFlowUpdateLocker.withLock {
            minDurationFlowUpdate.emit(minDurationInSecondsFlow.value + 10)
        }
    }

    override suspend fun decrementMinDuration() {
        minDurationFlowUpdateLocker.withLock {
            minDurationFlowUpdate.emit(minDurationInSecondsFlow.value - 10)
        }
    }

    private val isThemeDarkChangeFlow = MutableSharedFlow<Boolean>(replay = 1)
    private val isThemeDarkFlow = flow<Boolean> {
        isThemeDarkChangeFlow.emit(
            dataStore.data
                .map {
                    it[IS_THEME_DARK_KEY] ?: true
                }.first()
        )

        isThemeDarkChangeFlow.collect {
            dataStore.edit { settings ->
                settings[IS_THEME_DARK_KEY] = it
            }
            emit(it)
        }
    }.stateIn(cs, SharingStarted.Companion.Eagerly, true)

    override fun isThemeDark(): StateFlow<Boolean> = isThemeDarkFlow


    override suspend fun isDarkThemeChange() {
        isThemeDarkChangeFlow.emit(!isThemeDarkFlow.value)
    }

    private companion object {
        private val MIN_DURATION_KEY = longPreferencesKey("min_duration")
        private val IS_THEME_DARK_KEY = booleanPreferencesKey("is_theme_dark")
    }
}