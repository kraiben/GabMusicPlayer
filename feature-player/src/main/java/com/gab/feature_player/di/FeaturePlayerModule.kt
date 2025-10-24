package com.gab.feature_player.di

import com.gab.feature_api_player.FeaturePlayerHolder
import com.gab.feature_player.holder.FeaturePlayerHolderImpl
import dagger.Binds
import dagger.Module

@Module
public interface FeaturePlayerModule {
    @Binds
    @FeaturePlayerScope
    public fun bindFeaturePlayerHolder(impl: FeaturePlayerHolderImpl): FeaturePlayerHolder
}