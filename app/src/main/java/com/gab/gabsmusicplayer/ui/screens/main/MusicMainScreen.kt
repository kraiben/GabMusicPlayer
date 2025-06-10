package com.gab.gabsmusicplayer.ui.screens.main

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gab.gabsmusicplayer.ui.ViewModelFactory
import com.gab.gabsmusicplayer.ui.allTracksScreen.AllTracksScreen
import com.gab.gabsmusicplayer.ui.general.MiniPlayer
import com.gab.gabsmusicplayer.ui.musicPlayerScreen.MusicPlayerScreen
import com.gab.gabsmusicplayer.ui.navigation.MusicNavGraph
import com.gab.gabsmusicplayer.ui.navigation.NavigationItem
import com.gab.gabsmusicplayer.ui.navigation.rememberNavigationState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MusicMainScreen(viewModelFactory: ViewModelFactory) {
    val navigationState = rememberNavigationState()
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val navItems = listOf<NavigationItem>(NavigationItem.AllTracks)
    val navBackStackEntry by navigationState.navHostController
        .currentBackStackEntryAsState()
    val scope = rememberCoroutineScope()
    val readExtStoragePermissionState = rememberPermissionState(
        permission =
        if (Build.VERSION.SDK_INT >= 33) Manifest.permission.READ_MEDIA_AUDIO
        else Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
    GetPermission(readExtStoragePermissionState)

    if (readExtStoragePermissionState.status.isGranted) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    navItems = navItems,
                    onItemClick = { screenRoute -> navigationState.navigateTo(screenRoute) },
                    isSelectedCheck = { screenRoute ->
                        navBackStackEntry?.destination?.hierarchy?.any {
                            it.route == screenRoute
                        } ?: false
                    }
                )
            }
        ) {

            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                bottomBar = {
                    if ((viewModel.title != "null") && (viewModel.imageUri.toString() != "")) {
                        MiniPlayer(
                            modifier = Modifier
                                .height(52.dp),
                            onClick = {scope.launch { sheetState.show() }},
                            isTrackPlaying = viewModel.isTrackPlaying,
                            title = viewModel.title,
                            artist = viewModel.artist,
                            albumArtUri = viewModel.imageUri,
                            onPreviousButtonClick = { viewModel.previousTrack() },
                            onPlayPauseButtonClick = { viewModel.playPauseChange() },
                            onNextButtonClick = { viewModel.nextTrack() }
                        )
                    }
                },
            ) {
                Box {
                    MusicNavGraph(
                        navHostController = navigationState.navHostController,
                        allTracksScreenContent = {
                            AllTracksScreen(
                                viewModelFactory,
                                onStartRandomButtonClickListener = { tracks ->
                                    viewModel.selectTrack(
                                        tracks = tracks, isShuffled = true, context = context,
                                        navigateToPlayer = { scope.launch { sheetState.show() } }
                                    )
                                },
                                onTrackClickListener = { p1, p2 ->
                                    viewModel.selectTrack(
                                        tracks = p1,
                                        startIndex = p2,
                                        context = context,
                                        navigateToPlayer = { scope.launch { sheetState.show() } }
                                    )
                                },
                                modifier = Modifier.padding(it),
                                menuButtonClickListener = {scope.launch{drawerState.open()}},
                                addInOrderOption = {viewModel.setNextTrack(it)},
                                snackbarHostState = snackbarHostState
                            )
                        }
                    )
                    if (sheetState.isVisible) {
                        ModalBottomSheet(
                            dragHandle = {},
                            sheetState = sheetState,
                            onDismissRequest = { scope.launch{ sheetState.hide() } }
                        ) {
                            MusicPlayerScreen(
                                isTrackPlaying = viewModel.isTrackPlaying,
                                currentPosition = viewModel.currentPosition,
                                title = viewModel.title,
                                artist = viewModel.artist,
                                duration = viewModel.duration,
                                imageUri = viewModel.imageUri,
                                isShuffled = viewModel.isShuffleModeSet,
                                isOneRepeating = viewModel.isRepeatingOne,
                                onPreviousButtonClick = { viewModel.previousTrack() },
                                onPlayPauseButtonClick = { viewModel.playPauseChange() },
                                onNextButtonClick = { viewModel.nextTrack() },
                                onSliderChange = { viewModel.onSliderChange(it) },
                                onShuffleButtonClick = { viewModel.shuffleStateChange() },
                                onRepeatModeButtonClick = { viewModel.isRepeatingOneStateChange() },
                            )
                        }
                    }
                }
            }
        }


    } else {
        Text(
            text = "Request Perms",
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { readExtStoragePermissionState.launchPermissionRequest() })
        )
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetPermission(permission: PermissionState) {

    LaunchedEffect(permission) {
        when (permission.status) {
            is PermissionStatus.Denied -> {
                permission.launchPermissionRequest()
            }

            PermissionStatus.Granted -> {}
        }
    }
}

@Composable
fun DrawerContent(
    onItemClick: (String) -> Unit,
    navItems: List<NavigationItem>,
    isSelectedCheck: (String) -> Boolean,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxHeight(), verticalArrangement = Arrangement.Center
    ) {
        navItems.forEach { item ->
            val isSelected = isSelectedCheck(item.screen.route)
            ListItem(
                modifier = Modifier
                    .clickable { onItemClick(item.screen.route) }
                    .width((LocalConfiguration.current.screenWidthDp * 0.75).dp)
                    .height(48.dp),
                leadingContent = {
                    Icon(
                        contentDescription = null,
                        imageVector = item.icon
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(item.titleResId),
                        fontWeight = FontWeight.W300,
                        fontSize = 24.sp
                    )
                }
            )

        }
    }
}