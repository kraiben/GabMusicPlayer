package com.gab.feature_all_tracks.di

import com.gab.feature_all_tracks.holder.AllTTracksFeatureHolder
import com.gab.feature_all_tracks.holder.AllTracksFeatureHolderImpl
import dagger.Binds
import dagger.Module

@Module
public interface AllTracksFeatureModule {

    @AllTracksFeatureScope
    @Binds
    public fun bindAllTracksFeatureHolder(impl: AllTracksFeatureHolderImpl): AllTTracksFeatureHolder
}