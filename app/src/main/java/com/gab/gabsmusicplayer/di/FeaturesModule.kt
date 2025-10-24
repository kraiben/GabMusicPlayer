package com.gab.gabsmusicplayer.di

import com.gab.feature_all_tracks.di.AllTracksFeatureComponent
import com.gab.feature_api_all_tracks.AllTTracksFeatureHolder
import com.gab.feature_api_options_menus.AllTracksScreenOptionsMenuHolder
import com.gab.feature_api_options_menus.PlaylistScreenMenuHolder
import com.gab.feature_api_player.FeaturePlayerHolder
import com.gab.feature_api_playlists.PlaylistFeatureHolder
import com.gab.feature_options_menus.di.TrackOptionsMenuComponent
import com.gab.feature_player.di.FeaturePlayerComponent
import com.gab.feature_playlists.di.FeaturePlaylistsComponent
import com.gab.navigation_module.di.NavigationModuleComponent
import com.gab.navigation_module.holder.NavigationHolder
import com.gab.theme_api_module.ThemeHolder
import com.gab.theme_module.di.ThemeComponent
import dagger.Module
import dagger.Provides

@Module
class FeaturesModule {

    @Provides
    fun provideAllTracksFeatureHolder(): AllTTracksFeatureHolder {
        return AllTracksFeatureComponent.get().getHolder()
    }
    @Provides
    fun provideAllTracksScreenOptionsMenuHolder(): AllTracksScreenOptionsMenuHolder {
        return TrackOptionsMenuComponent.get().getAllTracksScreenOptionsMenuHolder()
    }
    @Provides
    fun providePlaylistScreenMenuHolder(): PlaylistScreenMenuHolder {
        return TrackOptionsMenuComponent.get().getPlaylistScreenMenuHolder()
    }
    @Provides
    fun providePlaylistFeatureHolder(): PlaylistFeatureHolder {
        return FeaturePlaylistsComponent.get().getHolder()
    }@Provides
    fun provideFeaturePlayerHolder(): FeaturePlayerHolder {
        return FeaturePlayerComponent.get().getHolder()
    }
    @Provides
    fun provideThemeHolder(): ThemeHolder {
        return ThemeComponent.get().getHolder()
    } @Provides
    fun provideNavigationHolder(): NavigationHolder {
        return NavigationModuleComponent.get().getHolder()
    }

}