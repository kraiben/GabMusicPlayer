package com.gab.feature_all_tracks.di

import com.gab.feature_all_tracks.holder.AllTracksFeatureHolderImpl
import com.gab.feature_api_all_tracks.AllTTracksFeatureHolder
import dagger.Binds
import dagger.Module

@Module
public interface AllTracksFeatureModule {

    @AllTracksFeatureScope
    @Binds
    public fun bindAllTracksFeatureHolder(impl: AllTracksFeatureHolderImpl): AllTTracksFeatureHolder
}