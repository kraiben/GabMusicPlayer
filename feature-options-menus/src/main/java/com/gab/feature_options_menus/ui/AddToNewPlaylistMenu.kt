package com.gab.feature_options_menus.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gab.feature_options_menus.ui.base.OptionsMenuDivider
import com.gab.feature_options_menus.ui.base.OptionsMenuElement
import com.gab.music_entities_module.TrackInfoModel

@Composable
internal fun AddToNewPlaylistMenu(
    track: TrackInfoModel,
    viewModel: TracksOptionsMenuViewModel,
    onDismiss: () -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.createPlaylistResultFlow.collect {
            if (it) onDismiss()
        }
    }
    var text by remember { mutableStateOf("") }
    OptionsMenuElement("Введите назвение") {}

    OptionsMenuDivider()
    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .padding(8.dp)
            .height(50.dp)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(50.dp)
                .clickable { onDismiss() }, text = "Отмена"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable { viewModel.createPlaylist(track, text) },
            text = "Сохранить"
        )
    }
}