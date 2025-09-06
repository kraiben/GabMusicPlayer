package com.gab.testapp.ui.screen

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(viewModelFactory: TestViewModel.Factory) {
    val viewModel: TestViewModel = viewModel(factory = viewModelFactory)
    val flow by viewModel.isInitialized.collectAsState()

    Text(
        text = flow.toString(),
        fontSize = 50.sp,
        modifier = Modifier
            .wrapContentHeight(align = Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}