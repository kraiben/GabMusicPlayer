package com.gab.feature_options_menus.ui.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun OptionsMenuElement(text: String, action: () -> Unit) {
    Text(
        textAlign = TextAlign.Companion.Start,
        maxLines = 2,
        overflow = TextOverflow.Companion.Ellipsis,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .clickable(onClick = action)
            .wrapContentHeight(align = Alignment.Companion.CenterVertically)
            .padding(8.dp),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Companion.W400
    )
}