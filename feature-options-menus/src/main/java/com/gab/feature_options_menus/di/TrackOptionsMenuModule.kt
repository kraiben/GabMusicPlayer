package com.gab.feature_options_menus.di

import com.gab.feature_api_options_menus.AllTracksScreenOptionsMenuHolder
import com.gab.feature_api_options_menus.PlaylistScreenMenuHolder
import com.gab.feature_options_menus.holder.AllTracksScreenOptionsMenuHolderImpl
import com.gab.feature_options_menus.holder.PlaylistScreenMenuHolderImpl
import dagger.Binds
import dagger.Module

@Module
public interface TrackOptionsMenuModule {

    @Binds
    @TrackOptionsMenuScope
    public fun getPlaylistScreenMenuHolder(
        impl: PlaylistScreenMenuHolderImpl
    ): PlaylistScreenMenuHolder

    @Binds
    @TrackOptionsMenuScope
    public fun getAllTracksScreenOptionsMenuHolder(
        impl: AllTracksScreenOptionsMenuHolderImpl
    ): AllTracksScreenOptionsMenuHolder

}