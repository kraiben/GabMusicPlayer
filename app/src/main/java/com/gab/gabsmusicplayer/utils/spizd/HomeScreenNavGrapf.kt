package com.gab.gabsmusicplayer.utils.spizd//package com.example.jcft.ui.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.composable
//import androidx.navigation.navArgument
//import androidx.navigation.navigation
//import com.example.jcft.domain.entities.FeedPost
//import com.google.gson.Gson
//
//fun NavGraphBuilder.homeScreenNavGraph(
//    newsFeedScreenContent: @Composable () -> Unit,
//    commentsScreenContent: @Composable (feedPost: FeedPost) -> Unit,
//
//    ) {
//    navigation(
//        startDestination = Screen.NewsFeed.route,
//        route = Screen.Home.route,
//    ) {
//        composable(Screen.NewsFeed.route) {
//            newsFeedScreenContent()
//        }
//        composable(
//            route = Screen.Comments.route,
//            arguments = listOf(
//            navArgument(Screen.KEY_FEED_POST) {
//            }
//        ) ){
//            val feedPostJson = it.arguments?.getString(Screen.KEY_FEED_POST) ?: ""
//            val feedPost: FeedPost = Gson().fromJson(feedPostJson, FeedPost::class.java)
//            commentsScreenContent(feedPost)
//        }
//        }
//}