package com.gab.core_settings.impl.di

import com.gab.core_settings.api.data.SettingsRepository
import com.gab.core_settings.impl.data.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
public interface CoreSettingsModule {

    @Binds
    public fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}