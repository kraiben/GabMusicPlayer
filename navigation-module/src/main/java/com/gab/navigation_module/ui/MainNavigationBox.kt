package com.gab.navigation_module.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainNavigationBox(
    bottomBarContent: @Composable (onClick: () -> Unit) -> Unit,
    drawerContent: @Composable (onNavItemClick: () -> Unit) -> Unit,
    modalBottomSheetContent: @Composable () -> Unit,
    mainContent: @Composable (onMenuClickListener: () -> Unit, paddingValues: PaddingValues, SnackbarHostState) -> Unit,

    ) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { drawerContent { scope.launch { drawerState.close() } } }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                bottomBarContent {
                    scope.launch {
                        sheetState.show()
                    }
                }
            },
        ) { paddingValues ->
            Box {
                val menuButtonClickListener: () -> Unit =
                    remember { { scope.launch { drawerState.open() } } }
                mainContent(menuButtonClickListener, paddingValues)
                AnimatedVisibility(
                    sheetState.isVisible
                ) {
                    ModalBottomSheet(
                        dragHandle = {},
                        sheetState = sheetState,
                        onDismissRequest = { scope.launch { sheetState.hide() } },
                        content = { modalBottomSheetContent() })
                }
            }
        }
    }
}