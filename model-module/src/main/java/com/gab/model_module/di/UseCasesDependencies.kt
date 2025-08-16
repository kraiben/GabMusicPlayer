package com.gab.model_module.di

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.core_settings.api.data.SettingsRepository

public interface UseCasesDependencies {

    public val musicLoadingRepository: MusicLoadingRepository
    public val settingsRepository: SettingsRepository

}