//package com.gab.gabsmusicplayer.ui.screens.main
//
//import androidx.compose.runtime.Composable
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.PermissionState
//import com.google.accompanist.permissions.PermissionStatus
//import android.Manifest
//import android.net.Uri
//import android.os.Build
//import androidx.activity.ComponentActivity
//import androidx.activity.SystemBarStyle
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.SideEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.toArgb
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavDestination.Companion.hierarchy
//import androidx.navigation.compose.currentBackStackEntryAsState
//import com.gab.gabsmusicplayer.ui.general.MiniPlayer
//import com.gab.feature_options_menus.TrackOptionsMenu
//import com.gab.feature_options_menus.TrackOptionsMenuState
//import com.gab.gabsmusicplayer.ui.musicPlayerScreen.MusicPlayerScreen
//import com.gab.gabsmusicplayer.ui.navigation.MusicNavGraph
//import com.gab.gabsmusicplayer.ui.navigation.NavigationItem
//import com.gab.gabsmusicplayer.ui.navigation.Screen
//import com.gab.gabsmusicplayer.ui.navigation.rememberNavigationState
//import com.gab.gabsmusicplayer.ui.playlistScreens.AllPlaylistsScreen
//import com.gab.gabsmusicplayer.ui.playlistScreens.MainViewModel
//import com.gab.gabsmusicplayer.ui.playlistScreens.PlaylistChangesScreenMode
//import com.gab.gabsmusicplayer.ui.playlistScreens.PlaylistEditOrAddScreen
//import com.gab.gabsmusicplayer.ui.playlistScreens.PlaylistScreenState
//import com.gab.gabsmusicplayer.ui.playlistScreens.SinglePlaylistScreen
//import com.gab.gabsmusicplayer.ui.theme.GabsMusicPlayerTheme
//import com.gab.gabsmusicplayer.utils.GAB_CHECK
//import com.gab.music_entities_module.TrackInfoModel
//import com.gab.music_entities_module.PlaylistInfoModel
//import com.gab.gabsmusicplayer.ui.player.MusicViewModel
//import com.google.accompanist.permissions.isGranted
//import com.google.accompanist.permissions.rememberPermissionState
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalPermissionsApi::class )
//@Composable
//fun MusicMainScreen(viewModelFactory: ViewModelProvider.Factory)
//{
//
//    val musicViewModel: MusicViewModel = viewModel(factory = viewModelFactory)
//    val mainViewModel: MainViewModel = viewModel(factory = viewModelFactory)
//
//    val navigationState = rememberNavigationState()
//    val scope = rememberCoroutineScope()
//    val context = LocalContext.current
//    val readExtStoragePermissionState = rememberPermissionState(
//        permission =
//        if (Build.VERSION.SDK_INT >= 33) Manifest.permission.READ_MEDIA_AUDIO
//        else Manifest.permission.READ_EXTERNAL_STORAGE
//    ) {mainViewModel.update()}
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
//    val snackbarHostState = remember { SnackbarHostState() }
//    val trackOptionsMenuState: MutableState<TrackOptionsMenuState> =
//        remember { mutableStateOf(TrackOptionsMenuState.NotVisible) }
//
//
//    val navBackStackEntry by navigationState.navHostController
//        .currentBackStackEntryAsState()
//    val destinationFlow = navigationState.navHostController.currentBackStackEntryAsState()
//    val navItems = listOf<NavigationItem>(
//        NavigationItem.AllTracks,
//        NavigationItem.AllPlaylists
//    )
//
//
//    val tracksState = mainViewModel.tracks.collectAsState(AllTracksScreenState.Initial)
//    val tracksWithoutDurationFilterState = mainViewModel.tracksWithoutDurationFilter
//        .collectAsState(AllTracksScreenState.Initial)
//    val playlistsState = mainViewModel.playlists.collectAsState(PlaylistScreenState.Initial)
//    val minDurationValueState = mainViewModel.getMinDurationFlow().collectAsState(94L)
//    val isThemeDarkState = mainViewModel.isThemeDark.collectAsState()
//    LaunchedEffect(Unit) {
//        mainViewModel.playlistCreationOrEditingResultFlow.collect {
//            if (!it) snackbarHostState
//                .showSnackbar(message = "Название плейлиста должно быть уникально")
//        }
//    }
//    val navigateToPlayer = remember { { scope.launch { sheetState.show() } } }
//    val onTrackClickListener  = remember {
//        {tracks: List<TrackInfoModel>, startIndex: Int?,   ->
//            musicViewModel.selectTrack(tracks = tracks, startIndex = startIndex ?: 0,
//                navigateToPlayer = {
//                    GAB_CHECK("navigateToPlayer")
//                    navigateToPlayer()
//                                   })
//        }
//    }
//    val addInOrder = remember { {it: TrackInfoModel -> musicViewModel.setNextTrack(it) } }
//    GabsMusicPlayerTheme(darkTheme = isThemeDarkState.value) {
//        (context as? ComponentActivity)?.enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.dark(
//                MaterialTheme.colorScheme.primaryContainer.toArgb()
//            ),
//            navigationBarStyle = SystemBarStyle.dark(
//                MaterialTheme.colorScheme.primaryContainer.toArgb()
//            )
//        )
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.background)
//                .statusBarsPadding()
//                .navigationBarsPadding()
//        ) {
//            if (readExtStoragePermissionState.status.isGranted) {
//                ModalNavigationDrawer(
//                    drawerState = drawerState,
//                    drawerContent = {
//                        DrawerContent(
//                            navItems = navItems,
//                            onItemClick = { screenRoute ->
//                                navigationState.navigateTo(screenRoute)
//                                scope.launch { drawerState.close() }
//                            },
//                            isSelectedCheck = { screenRoute ->
//                                navBackStackEntry?.destination?.hierarchy?.any {
//                                    it.route == screenRoute
//                                } ?: false
//                            },
//                            minDuration = minDurationValueState.value,
//                            incrementMinDuration = { mainViewModel.incrementMinDuration() },
//                            decrementMinDuration = { mainViewModel.decrementMinDuration() },
//                            isThemeDark = isThemeDarkState.value,
//                            isThemeDarkChange = { mainViewModel.isDarkThemeChange() }
//                        )
//                    }
//                ) {
//                    Scaffold(
//                        snackbarHost = { SnackbarHost(snackbarHostState) },
//                        bottomBar = {
//                            if ((musicViewModel.title != "")
//                                && (destinationFlow.value?.destination?.route != Screen.PlaylistEditOrAddScreen.route)
//                            ) {
//                                MiniPlayer(
//                                    modifier = Modifier
//                                        .height(52.dp),
//                                    onClick = { scope.launch { sheetState.show() } },
//                                    isTrackPlaying = musicViewModel.isTrackPlaying,
//                                    title = musicViewModel.title,
//                                    artist = musicViewModel.artist,
//                                    albumArtUri = musicViewModel.imageUri,
//                                    artworkData = musicViewModel.artworkData,
//                                    onPreviousButtonClick = { musicViewModel.previousTrack() },
//                                    onPlayPauseButtonClick = { musicViewModel.playPauseChange() },
//                                    onNextButtonClick = { musicViewModel.nextTrack() }
//                                )
//                            }
//                        },
//                    ) { paddingValues ->
//                        Box {
//                            val menuButtonClickListener = remember {{ scope.launch { drawerState.open() } }}
//
//                            MusicNavGraph(
//                                navHostController = navigationState.navHostController,
//                                playlistScreenContent = { playlistId ->
//                                    SinglePlaylistScreen(
//                                        playlistId,
//                                        onTrackClickListener = onTrackClickListener,
//                                        onMainPlayButtonClickListener = { tracks ->
//                                            musicViewModel.selectTrack(
//                                                tracks = tracks,
//                                                isShuffled = true,
//                                                navigateToPlayer = { scope.launch { sheetState.show() } }
//                                            )
//                                        },
//                                        playlistsState = playlistsState.value,
//                                        setPlaylistPicture = { playlist, uri ->
//                                            mainViewModel.setPlaylistPicture(
//                                                playlist = playlist,
//                                                uri = uri
//                                            )
//                                        },
//                                        playlistEdit = { playlist ->
//                                            navigationState.navigateToPlaylistEditScreen(
//                                                playlist, PlaylistChangesScreenMode.EditMode
//                                            )
//                                        },
//                                        paddingValues = paddingValues,
//                                        trackOptionsMenuClickListener = { track, playlist ->
//                                            trackOptionsMenuState.value =
//                                                TrackOptionsMenuState.PlaylistScreenMenu(
//                                                    track,
//                                                    playlist
//                                                )
//                                        }
//                                    )
//                                },
//                                allPlaylistsGridContent = {
//                                    AllPlaylistsScreen(
//                                        paddingValues = paddingValues,
//                                        navigationState = navigationState,
//                                        playlistsState = playlistsState.value,
//                                        onCreateNewPlaylist = {
//                                            navigationState.navigateToPlaylistEditScreen(
//                                                PlaylistInfoModel.EMPTY,
//                                                PlaylistChangesScreenMode.CreatingMode
//                                            )
//                                        }
//                                    )
//                                },
//                                playlistEditOrAddScreenContent = { playlist, screenMode ->
//                                    PlaylistEditOrAddScreen(
//                                        playlist = playlist,
//                                        allTracks = tracksWithoutDurationFilterState.value,
//                                        playlistCreationResultFlow = mainViewModel.playlistCreationOrEditingResultFlow,
//                                        screenMode = screenMode,
//                                        snackbarHostState = snackbarHostState,
//                                        onCreatePlaylist = { tracks, title, coverUri ->
//                                            mainViewModel.createPlaylist(tracks, title, coverUri)
//                                        },
//                                        onSavePlaylistChanges = { pId, tracks, title, coverUri ->
//                                            mainViewModel.changePlaylist(
//                                                pId,
//                                                tracks,
//                                                title,
//                                                coverUri
//                                            )
//                                        },
//                                        onReturn = {
//                                            navigationState.returnFromPlaylistEditOrAddScreen()
//                                        },
//                                        onRemovePlaylist = { p -> mainViewModel.removePlaylist(p) },
//                                        onReturnAfterRemoving = {
//                                            navigationState
//                                                .returnFromPlaylistEditOrAddScreenAfterRemovingPlaylist()
//                                        }
//                                    )
//                                },
//                                allTracksScreenContent = {
//                                    val trackOptionsMenuClickListener = remember {
//                                        { track: TrackInfoModel ->
//                                            trackOptionsMenuState.value = TrackOptionsMenuState
//                                                .AllTracksScreenMenu(track)
//                                        }
//                                    }
//                                    AllTracksScreen(
//                                        onStartRandomButtonClickListener = { tracks ->
//                                            musicViewModel.selectTrack(
//                                                tracks = tracks,
//                                                isShuffled = true,
//                                                navigateToPlayer = { scope.launch { sheetState.show() } }
//                                            )
//                                        },
//                                        onTrackClickListener = onTrackClickListener,
//                                        padding = paddingValues,
//                                        menuButtonClickListener = { menuButtonClickListener() },
//                                        addInOrderOption = addInOrder,
//                                        snackbarHostState = snackbarHostState,
//                                        trackOptionsMenuClickListener = trackOptionsMenuClickListener,
//                                        tracksState = tracksState.value
//                                    )
//                                }
//                            )
//                            AnimatedVisibility(
//                                sheetState.isVisible
//                            ) {
//                                ModalBottomSheet(
//                                    dragHandle = {},
//                                    sheetState = sheetState,
//                                    onDismissRequest = { scope.launch { sheetState.hide() } }
//                                ) {
//                                    MusicPlayerScreen(
//                                        viewModel = musicViewModel,
//                                    )
//                                }
//                            }
//                            TrackOptionsMenu(
//                                onDismiss = {
//                                    trackOptionsMenuState.value = TrackOptionsMenuState.NotVisible
//                                },
//                                modifier = Modifier,
//                                playlistsState = playlistsState.value,
//                                state = trackOptionsMenuState.value,
//                                creatingPlaylistResultFlow = mainViewModel.playlistCreationOrEditingResultFlow,
//                                playNext = addInOrder,
//                                createPlaylist = { track, title ->
//                                    mainViewModel.createPlaylist(
//                                        tracks = listOf(track), title = title, coverUri = Uri.EMPTY
//                                    )
//                                },
//                                goToAddInPlaylistMenu = { t ->
//                                    trackOptionsMenuState.value =
//                                        TrackOptionsMenuState.AddToPlaylistMenu(t)
//                                },
//                                goToAddInNewPlaylistMenu = { t ->
//                                    trackOptionsMenuState.value =
//                                        TrackOptionsMenuState.AddToNewPlaylistMenu(t)
//                                },
//                                addToPlaylist = { track, playlist ->
//                                    mainViewModel.addToPlaylist(playlist, track)
//                                },
//                                removeFromPlaylist = { track, playlist ->
//                                    mainViewModel.removeFromPlaylist(playlist, track)
//                                },
//                            )
//                        }
//
//                    }
//                }
//            } else {
//                SideEffect {
//                    readExtStoragePermissionState.launchPermissionRequest()
//                }
//                Text(
//                    text = "Request Perms",
//                    fontSize = 40.sp,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clickable(onClick = { readExtStoragePermissionState.launchPermissionRequest() })
//                )
//
//            }
//        }
//    }
//}
//
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun GetPermission(permission: PermissionState) {
//
//    LaunchedEffect(permission) {
//        when (permission.status) {
//            is PermissionStatus.Denied -> {
//                permission.launchPermissionRequest()
//            }
//
//            PermissionStatus.Granted -> {}
//        }
//    }
//}
