package com.gab.gabsmusicplayer.di

import com.gab.feature_all_tracks.di.AllTracksFeatureComponent
import com.gab.feature_all_tracks.holder.AllTTracksFeatureHolder
import com.gab.feature_options_menus.di.TrackOptionsMenuComponent
import com.gab.feature_options_menus.holder.TrackOptionsMenuFeatureHolder
import com.gab.feature_playlists.di.FeaturePlaylistsComponent
import com.gab.feature_playlists.holder.PlaylistFeatureHolder
import dagger.Module
import dagger.Provides

@Module
class FeaturesModule {

    @Provides
    fun provideAllTracksFeatureHolder(): AllTTracksFeatureHolder {
        return AllTracksFeatureComponent.get().getHolder()
    }
    @Provides
    fun provideTrackOptionsMenuHolder(): TrackOptionsMenuFeatureHolder {
        return TrackOptionsMenuComponent.get().getHolder()
    }
    @Provides
    fun providePlaylistFeatureHolder(): PlaylistFeatureHolder {
        return FeaturePlaylistsComponent.get().getHolder()
    }

}