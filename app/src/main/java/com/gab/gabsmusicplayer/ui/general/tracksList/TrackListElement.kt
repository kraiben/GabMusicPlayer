package com.gab.gabsmusicplayer.ui.general.tracksList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel

@Composable
fun TrackListElement(
    track: TrackInfoModel,
    modifier: Modifier,
    menuButtonClickListener: () -> Unit,
) {
    ListItem(
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        modifier = modifier,
        leadingContent = {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f), contentDescription = null,
                model = (ImageRequest.Builder(LocalContext.current)
                    .data(track.albumArtUri)
                    .error(track.albumArtUriIsNullPatchId)
                    .memoryCacheKey("${track.albumArtUri}-${track.albumArtUriIsNullPatchId}")

                    .build())
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
                        menuButtonClickListener()
                    }
                    .padding(vertical = 8.dp)
                    .fillMaxHeight()
            )
        }

    )
}