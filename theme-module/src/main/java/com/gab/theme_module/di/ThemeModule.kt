package com.gab.theme_module.di

import com.gab.theme_api_module.ThemeHolder
import com.gab.theme_module.holder.ThemeHolderImpl
import dagger.Binds
import dagger.Module

@Module
public interface ThemeModule {
    @Binds
    @ThemeScope
    public fun bindFeaturePlaylistsHolder(impl: ThemeHolderImpl): ThemeHolder
}