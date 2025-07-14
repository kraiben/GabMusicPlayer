package com.gab.gabsmusicplayer.ui.general

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SortChip(
    sortParameter: SortParameter,
    sortOrderState: SortOrderState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
    ) {
    val icon = if (sortParameter != sortOrderState.parameter) Icons.Filled.Circle
    else when (sortOrderState.order) {
        SortOder.Asc -> Icons.Default.KeyboardArrowUp
        SortOder.Desc -> Icons.Default.KeyboardArrowDown
    }

    InputChip(
        modifier = modifier,
        shape = RoundedCornerShape(40),
        selected = true,
        onClick = onClick,
        label = { Text(sortParameter.orderBy) },
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        },
        colors = InputChipDefaults.inputChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}