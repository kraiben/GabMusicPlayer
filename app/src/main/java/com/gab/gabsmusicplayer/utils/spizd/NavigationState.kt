//package com.example.jcft.ui.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.navigation.NavGraph.Companion.findStartDestination
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.example.jcft.domain.entities.FeedPost
//
//class NavigationState(
//    val navHostController: NavHostController
//) {
//    fun navigateTo(route: String) {
//        navHostController.navigate(route) {
//            popUpTo(navHostController.graph.findStartDestination().id) {
//                saveState = true
//            }
//            restoreState = true
//            launchSingleTop = true
//        }
//    }
//
//    fun navigateToComments(feedPost: FeedPost) {
//        navHostController.navigate(Screen.Comments.getRouteWithArgs(feedPost))
//    }
//}
//
//@Composable
//fun rememberNavigationState(navHostController: NavHostController = rememberNavController()) : NavigationState {
//    return remember {
//        NavigationState(navHostController)
//    }
//}
//
