package com.gab.gabsmusicplayer.di.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gab.feature_all_tracks.ui.AllTracksViewModel
import com.gab.feature_options_menus.ui.TracksOptionsMenuViewModel
import com.gab.feature_player.ui.PlayerViewModel
import com.gab.feature_playlists.ui.PlaylistsFeatureViewModel
import com.gab.navigation_module.ui.NavigationViewModel
import com.gab.theme_module.ui.ThemeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
public interface ViewModelModule {
    @Binds
    public fun bindViewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AllTracksViewModel::class)
    public fun bindAllTracksViewModel(impl: AllTracksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TracksOptionsMenuViewModel::class)
    public fun bindTracksOptionsMenuViewModel(impl: TracksOptionsMenuViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlaylistsFeatureViewModel::class)
    public fun bindPlaylistsFeatureViewModel(impl: PlaylistsFeatureViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ThemeViewModel::class)
    public fun bindThemeViewModel(impl: ThemeViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel::class)
    public fun bindPlayerViewModel(impl: PlayerViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(NavigationViewModel::class)
    public fun bindNavigationViewodel(impl: NavigationViewModel): ViewModel

}