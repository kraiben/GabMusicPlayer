package com.gab.gabsmusicplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MusicNavGraph(
    navHostController: NavHostController,
//    audioPlayerScreenContent: @Composable () -> Unit,
    allTracksScreenContent: @Composable () -> Unit,
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.AllPlaylistsScreen.route
    ) {
//        composable(
//            route = Screen.AudioPlayerScreen.route,
//
//        ) {
//            audioPlayerScreenContent()
//        }
        composable(
            route = Screen.AllPlaylistsScreen.route
            ) {

            allTracksScreenContent()
        }
    }

}