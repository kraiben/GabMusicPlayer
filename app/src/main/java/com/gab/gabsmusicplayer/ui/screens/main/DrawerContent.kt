package com.gab.gabsmusicplayer.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gab.gabsmusicplayer.ui.navigation.NavigationItem

@Composable
fun DrawerContent(
    onItemClick: (String) -> Unit,
    navItems: List<NavigationItem>,
    isSelectedCheck: (String) -> Boolean,
    minDuration: Long,
    incrementMinDuration: () -> Unit,
    decrementMinDuration: () -> Unit,
    isThemeDark: Boolean,
    isThemeDarkChange: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .padding(end = 16.dp)
            .fillMaxWidth(0.75f)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .fillMaxHeight(), verticalArrangement = Arrangement.Center
    ) {
        navItems.forEach { item ->
            val isSelected = isSelectedCheck(item.screen.route)
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                modifier = Modifier
                    .clickable { onItemClick(item.screen.route) }
                    .fillMaxWidth()
                    .height(48.dp),
                leadingContent = {
                    Icon(
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentDescription = null,
                        imageVector = item.icon
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(item.titleResId),
                        fontWeight = FontWeight.W500,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            )
            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        var duration by remember { mutableStateOf(minDuration.toString()) }
        LaunchedEffect(minDuration) { duration = minDuration.toString() }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .fillMaxWidth(0.5f),
                text = "Длительность:",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.W400,
            )
            Icon(
                imageVector = Icons.Outlined.RemoveCircleOutline,
                contentDescription = null,
                modifier = Modifier
                    .clickable { decrementMinDuration() }
                    .size(30.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(modifier = Modifier.padding(horizontal = 16.dp), text = duration,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,)
            Icon(
                imageVector = Icons.Outlined.AddCircleOutline,
                contentDescription = null,
                modifier = Modifier
                    .clickable { incrementMinDuration() }
                    .size(30.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        HorizontalDivider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .fillMaxWidth(0.5f),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                text = "Темный режим",
                fontWeight = FontWeight.W400
            )
            Switch(
                isThemeDark,
                onCheckedChange = { isThemeDarkChange() },
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}