package com.gab.feature_playlists.ui.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gab.music_entities_module.TrackInfoModel

@Composable
internal fun TrackListElement(
    track: TrackInfoModel,
    modifier: Modifier,
    menuButtonClickListener: (TrackInfoModel) -> Unit,
    colors: ListItemColors = ListItemDefaults.colors(
        containerColor = Color.Transparent
    ),
) {
    ListItem(
        colors = colors,
        modifier = modifier,
        leadingContent = {
            val context = LocalContext.current
            val imageRequest = remember {
                ImageRequest.Builder(context)
                    .data(if (track.albumArtUri == Uri.EMPTY) track.albumArtUriIsNullPatchId
                    else track.albumArtUri)
                    .build()
            }
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f), contentDescription = null,
                model = (imageRequest)
            )
        },

        headlineContent = {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = track.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall
            )
        },
        supportingContent = {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = track.artist,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
        },
        trailingContent = {
            Icon(
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
                imageVector = Icons.Default.MoreVert,
                modifier = Modifier
                    .clickable {
                        menuButtonClickListener(track)
                    }
                    .padding(vertical = 8.dp)
                    .fillMaxHeight()
            )
        }

    )
}