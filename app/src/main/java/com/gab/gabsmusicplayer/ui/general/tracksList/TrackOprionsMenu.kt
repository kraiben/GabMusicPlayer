package com.gab.gabsmusicplayer.ui.general.tracksList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun OptionsMenuContainer(onDismiss: () -> Unit, content: @Composable (ColumnScope.() -> Unit)) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .clip(
                    shape = RoundedCornerShape(
                        topEnd = 16f, topStart = 16f
                    )
                )
                .wrapContentHeight()
                .padding(8.dp)
                .fillMaxWidth(),
            content = content
        )
    }

}

@Composable
fun OptionsMenuElement(text: String, action: () -> Unit) {
    Text(
        modifier = Modifier
            .clickable(onClick = action)
            .height(40.dp),
        color = Color.Black,
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.W400
    )
}