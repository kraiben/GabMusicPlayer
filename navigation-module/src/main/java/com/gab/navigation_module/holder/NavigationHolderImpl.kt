package com.gab.navigation_module.holder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistPlay
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gab.feature_api_all_tracks.AllTTracksFeatureHolder
import com.gab.feature_api_player.FeaturePlayerHolder
import com.gab.feature_api_playlists.PlaylistFeatureHolder
import com.gab.gabsmusicplayer.ui.screens.main.Base
import com.gab.gabsmusicplayer.ui.screens.main.NavDrawer
import com.gab.navigation_module.R
import com.gab.navigation_module.ui.MainNavigationBox
import com.gab.navigation_module.ui.NavigationItem
import com.gab.navigation_module.ui.NavigationViewModel
import com.gab.theme_api_module.ThemeHolder
import kotlinx.coroutines.launch
import javax.inject.Inject

public class NavigationHolderImpl @Inject constructor(
    private val themeHolder: ThemeHolder,
    private val allTracksFeatureHolder: AllTTracksFeatureHolder,
    private val playlistFeatureHolder: PlaylistFeatureHolder,
    private val featurePlayerHolder: FeaturePlayerHolder,
    private val viewModelFactory: ViewModelProvider.Factory,
) : NavigationHolder {
    private val allTracksNavItem = NavigationItem(
        route = allTracksFeatureHolder.getAllTracksScreenRoute(),
        titleResId = R.string.all_tracks_title,
        icon = Icons.Default.Home,
    )
    private val playlistsNavItem = NavigationItem(
        route = playlistFeatureHolder.getPlaylistFeatureRoute(),
        titleResId = R.string.playlists_title,
        icon = Icons.AutoMirrored.Filled.PlaylistPlay
    )

    @Composable
    override fun GetNavGraph() {
        themeHolder.Theme {
            val scope = rememberCoroutineScope()
            val navHostController = rememberNavController()
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            Base {
                val viewModel = viewModel<NavigationViewModel>(factory = viewModelFactory)
                val minDuration by viewModel.getMinDuration().collectAsState()
                val incrementMinDuration = remember { { viewModel.incrementDuration() } }
                val decrementMinDuration = remember { { viewModel.decrementDuration() } }
                val isThemeDark by viewModel.getIsDarkThemeSet().collectAsState()
                val isThemeDarkChange = remember { { viewModel.isDarkThemeChange() } }
                MainNavigationBox(
                    bottomBarContent = { bbc ->
                        featurePlayerHolder.MiniPlayer(
                            modifier = Modifier
                                .height(52.dp)
                                .clickable { bbc() }
                        )
                    }, { onNavItemClick ->
                        NavDrawer(
                            onItemClick = {
                                navHostController.navigateTo(it)
                                onNavItemClick()
                            },
                            navItems = listOf(allTracksNavItem, playlistsNavItem),
                            isSelectedCheck = { screenRoute ->
                                navBackStackEntry?.destination?.hierarchy?.any {
                                    it.route == screenRoute
                                } ?: false
                            },
                            minDuration = minDuration,
                            incrementMinDuration = incrementMinDuration,
                            decrementMinDuration = decrementMinDuration,
                            isThemeDark = isThemeDark,
                            isThemeDarkChange = isThemeDarkChange,
                        )
                    },
                    {
                        featurePlayerHolder.MusicPlayerScreen()
                    },
                    { onMenuClickListener, paddingValues, snackbar ->
                        NavHost(
                            navHostController,
                            allTracksFeatureHolder.getAllTracksScreenRoute(),
                        ) {
                            with(allTracksFeatureHolder) {
                                allTracksScreen(
                                    modifier = Modifier
                                        .padding(paddingValues)
                                        .fillMaxSize(),
                                    onMenuClickListener,
                                    { s ->
                                        scope.launch {
                                            snackbar.showSnackbar(
                                                s,
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                )
                            }
                            with(playlistFeatureHolder) {
                                playlistsFeatureNavGraph(
                                    navHostController = navHostController,
                                    modifier = Modifier
                                        .padding(paddingValues)
                                        .fillMaxSize(),
                                    pictureSelectingErrorMsg = { s ->
                                        scope.launch {
                                            snackbar.showSnackbar(
                                                s,
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    },
                                    notUniquePlaylistNameErrorMessage = { s ->
                                        scope.launch {
                                            snackbar.showSnackbar(
                                                s,
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                )
            }

        }
    }
}

private fun NavHostController.navigateTo(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateTo.graph.findStartDestination().id) {
            saveState = true
        }
        restoreState = true
        launchSingleTop = true
    }
}