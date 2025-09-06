package com.gab.feature_playlists.holder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel

public interface PlaylistFeatureHolder {

    public fun NavGraphBuilder.playlistsFeatureNavGraph(
        navHostController: NavHostController,
        modifier: Modifier,
        viewModelFactory: ViewModelProvider.Factory,
        trackOptionsMenuContent: @Composable (TrackInfoModel, PlaylistInfoModel, () -> Unit) -> Unit,
        pictureSelectingErrorMsg: (String) -> Unit,
        )

    public fun getPlaylistFeatureRoute(): String

}