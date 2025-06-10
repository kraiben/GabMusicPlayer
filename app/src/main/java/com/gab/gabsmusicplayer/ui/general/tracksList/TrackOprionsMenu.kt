package com.gab.gabsmusicplayer.ui.general.tracksList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.gab.gabsmusicplayer.utils.GAB_CHECK

@Composable
fun TrackOptionsMenu(
    addInQueryOption : () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.background(Color.LightGray).clip(
                shape = RoundedCornerShape(
                    topEnd = 16f, topStart = 16f
                )
            )
                .wrapContentHeight()
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                    GAB_CHECK(2)
                    addInQueryOption()
                    onDismiss()
                }.height(60.dp),
                color = Color.Black,
                text = "Добавить в очередь",
                fontSize = 24.sp,
                fontWeight = FontWeight.W400
            )

        }
    }
}