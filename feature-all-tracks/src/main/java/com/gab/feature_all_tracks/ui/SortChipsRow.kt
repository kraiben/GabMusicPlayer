package com.gab.feature_all_tracks.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun SortChipsRow(
    selectedSortParameter: SortParameter?,
    onClick: (SortParameter) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val textSize = 16.sp
        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            text = "Сортировка по:",
            fontSize = textSize,
            color = MaterialTheme.colorScheme.onBackground
        )
        SortChip(
            onClick = onClick,
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            sortParameter = SortParameter.Date,
            selectedSortParameter = selectedSortParameter
        )
        SortChip(
            onClick = onClick,
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            sortParameter = SortParameter.Title,
            selectedSortParameter = selectedSortParameter
        )
    }
}

@Composable
private fun SortChip(
    modifier: Modifier = Modifier,
    sortParameter: SortParameter,
    selectedSortParameter: SortParameter?,
    onClick: (SortParameter) -> Unit,
) {
    InputChip(
        modifier = modifier,
        shape = RoundedCornerShape(40),
        selected = sortParameter == selectedSortParameter,
        onClick = { onClick(sortParameter) },
        label = { Text(sortParameter.orderBy) },
        colors = InputChipDefaults.inputChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}