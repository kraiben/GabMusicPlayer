package com.gab.core_settings.api.di

import com.gab.core_settings.api.data.SettingsRepository

public interface SettingsApi {
    public fun settingsRepository(): SettingsRepository
}