//package com.example.jcft.ui.navigation
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.outlined.Person
//import androidx.compose.ui.graphics.vector.ImageVector
//import com.example.jcft.R
//
//sealed class NavigationItemSW(
//    val screen: Screen,
//    val titleResId: Int,
//    val icon: ImageVector
//) {
//    object Home: NavigationItemSW(
//        screen = Screen.Home,
//        titleResId = R.string.navigation_item_main,
//        icon = Icons.Filled.Home
//    )
//
//    object Favourite: NavigationItemSW(
//        screen = Screen.Favourite,
//        titleResId = R.string.navigation_item_favourite,
//        icon = Icons.Filled.Favorite
//    )
//
//    object Profile: NavigationItemSW(
//        screen = Screen.Profile,
//        titleResId = R.string.navigation_item_profile,
//        icon = Icons.Outlined.Person
//    )
//
//
//}