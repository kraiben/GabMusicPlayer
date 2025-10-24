package com.gab.feature_api_playlists

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

public interface PlaylistFeatureHolder {

    public fun NavGraphBuilder.playlistsFeatureNavGraph(
        navHostController: NavHostController,
        modifier: Modifier,
        pictureSelectingErrorMsg: (String) -> Unit,
        notUniquePlaylistNameErrorMessage: (String) -> Unit,)

    public fun getPlaylistFeatureRoute(): String

}