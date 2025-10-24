package com.gab.feature_playlists.holder

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.gab.feature_api_options_menus.PlaylistScreenMenuHolder
import com.gab.feature_api_playlists.PlaylistFeatureHolder
import com.gab.feature_playlists.ui.AllPlaylistsScreen
import com.gab.feature_playlists.ui.PlaylistEditOrAddScreen
import com.gab.feature_playlists.ui.SinglePlaylistScreen
import com.gab.feature_playlists.ui.navigation.NavControllerExtras.navigateToPlaylistCreatingScreen
import com.gab.feature_playlists.ui.navigation.NavControllerExtras.navigateToPlaylistEditScreen
import com.gab.feature_playlists.ui.navigation.NavControllerExtras.navigateToSinglePlaylistScreen
import com.gab.feature_playlists.ui.navigation.NavControllerExtras.returnFromPlaylistEditOrAddScreen
import com.gab.feature_playlists.ui.navigation.NavControllerExtras.returnFromPlaylistEditOrAddScreenAfterRemovingPlaylist
import com.gab.feature_playlists.ui.navigation.PlaylistChangesScreenMode
import com.gab.feature_playlists.ui.navigation.Screen
import com.gab.feature_playlists.ui.navigation.playlistNavGraph
import com.gab.music_entities_module.PlaylistInfoModel
import javax.inject.Inject

public class PlaylistFeatureHolderImpl @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val playlistScreenMenuHolder: PlaylistScreenMenuHolder
): PlaylistFeatureHolder {
    override fun NavGraphBuilder.playlistsFeatureNavGraph(
        navHostController: NavHostController,
        modifier: Modifier,
        pictureSelectingErrorMsg: (String) -> Unit,
        notUniquePlaylistNameErrorMessage: (String) -> Unit,
    ) {
        playlistNavGraph(
            allPlaylistsGridContent = {
                AllPlaylistsScreen(
                    modifier = Modifier,
                    viewModelFactory = viewModelFactory,
                    navigateToPlaylist = { navHostController.navigateToSinglePlaylistScreen(it) },
                    onCreateNewPlaylistScreenNavigate = { navHostController.navigateToPlaylistCreatingScreen() })
            },
            playlistScreenContent = { id ->
                SinglePlaylistScreen(
                    modifier = modifier,
                    playlistId = id,
                    playlistScreenMenuHolder = playlistScreenMenuHolder,
                    viewModelFactory = viewModelFactory,
                    navigateToPlaylistEditScreen = { playlist ->
                        navHostController.navigateToPlaylistEditScreen(
                            playlist
                        )
                    }
                )
            },
            playlistEditOrAddScreenContent = { playlist: PlaylistInfoModel, screenMode: PlaylistChangesScreenMode ->
                PlaylistEditOrAddScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModelFactory = viewModelFactory,
                    playlist = playlist,
                    screenMode = screenMode,
                    pictureSelectingErrorMsg = pictureSelectingErrorMsg,
                    notUniquePlaylistNameErrorMessage = notUniquePlaylistNameErrorMessage,
                    onReturn = {
                        navHostController.returnFromPlaylistEditOrAddScreen()
                    },
                    onReturnAfterRemoving = { navHostController.returnFromPlaylistEditOrAddScreenAfterRemovingPlaylist() })
            })

    }

    override fun getPlaylistFeatureRoute(): String = Screen.Playlist.route

//    override fun navigateToPlaylistFeature(navHostController: NavHostController) {
//        navHostController.navigate(Screen.Playlist.route) {
//            popUpTo(navHostController.graph.findStartDestination().id) {
//                saveState = true
//            }
//            restoreState = true
//            launchSingleTop = true
//        }
//    }
}