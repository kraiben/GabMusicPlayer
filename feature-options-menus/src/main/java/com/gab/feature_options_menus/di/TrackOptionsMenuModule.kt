package com.gab.feature_options_menus.di

import com.gab.feature_options_menus.holder.TrackOptionsMenuFeatureHolder
import com.gab.feature_options_menus.holder.TrackOptionsMenuFeatureHolderImpl
import dagger.Binds
import dagger.Module

@Module
public interface TrackOptionsMenuModule {

    @Binds
    @TrackOptionsMenuScope
    public fun getTrackOptionsMenuFeatureHolder(
        impl: TrackOptionsMenuFeatureHolderImpl
    ): TrackOptionsMenuFeatureHolder

}