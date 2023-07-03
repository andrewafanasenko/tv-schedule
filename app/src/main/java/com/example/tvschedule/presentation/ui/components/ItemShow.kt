package com.example.tvschedule.presentation.ui.components

import android.text.Html
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tvschedule.presentation.search.model.ShowItem


@Composable
fun ItemShow(
    show: ShowItem,
    modifier: Modifier = Modifier,
    onFavouriteClick: (id: Long, isFavorite: Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .then(modifier)
    ) {
        MainContent(show)
        ImageCard(
            coverUrl = show.coverUrl,
            modifier = Modifier
                .fillMaxHeight()
                .width(120.dp)
                .padding(bottom = 8.dp)
        )
        FavouriteButton(
            isFavourite = show.isFavourite,
            modifier = Modifier.align(Alignment.TopEnd),
            onFavouriteClick = {
                onFavouriteClick.invoke(show.id, it)
            }
        )
        show.rating?.let {
            RatingPill(
                rating = it,
                modifier = Modifier
                    .padding(start = 60.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MainContent(show: ShowItem) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 60.dp, top = 24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 76.dp, top = 16.dp, bottom = 12.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = show.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                modifier = Modifier.weight(1f),
                text = Html.fromHtml(show.summary, Html.FROM_HTML_MODE_LEGACY).toString(),
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            if (show.genres.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    show.genres.forEach { genre ->
                        AssistChip(
                            modifier = Modifier
                                .height(22.dp)
                                .padding(2.dp),
                            onClick = { /* Ignored */ },
                            label = {
                                Text(
                                    text = genre,
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FavouriteButton(
    isFavourite: Boolean,
    modifier: Modifier = Modifier,
    onFavouriteClick: (Boolean) -> Unit
) {
    FilledIconToggleButton(
        modifier = modifier,
        checked = isFavourite,
        colors = IconButtonDefaults.iconToggleButtonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            checkedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        onCheckedChange = onFavouriteClick
    ) {
        val tint by animateColorAsState(
            targetValue = if (isFavourite) Color.Red else Color.LightGray
        )
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = tint
        )
    }
}
