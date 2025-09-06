package com.gab.feature_playlists.di

import com.gab.feature_playlists.holder.PlaylistFeatureHolder
import com.gab.feature_playlists.holder.PlaylistFeatureHolderImpl
import dagger.Binds
import dagger.Module

@Module
public interface FeaturePlaylistsModule {
    @Binds
    @FeaturePlaylistsScope
    public fun bindFeaturePlaylistsHolder(impl: PlaylistFeatureHolderImpl): PlaylistFeatureHolder
}