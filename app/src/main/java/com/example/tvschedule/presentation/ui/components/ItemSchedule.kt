package com.example.tvschedule.presentation.ui.components

import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.tvschedule.R
import com.example.tvschedule.presentation.schedule.model.ScheduleItem
import com.example.tvschedule.presentation.ui.theme.Gold

@Composable
fun ItemSchedule(schedule: ScheduleItem, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .then(modifier)
    ) {
        MainContent(schedule)
        ImageCard(schedule.coverUrl)
        SeasonAndEpisode(schedule, Modifier.align(Alignment.TopEnd))
        Rating(schedule, Modifier.align(Alignment.BottomStart))
    }
}

@Composable
private fun MainContent(schedule: ScheduleItem) {
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
                .padding(start = 76.dp, top = 12.dp, bottom = 12.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = schedule.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = schedule.episodeName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight
                ),
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                modifier = Modifier.weight(1f),
                text = Html.fromHtml(schedule.summary, Html.FROM_HTML_MODE_LEGACY).toString(),
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
            ) {
                if (schedule.time.isNotBlank()) {
                    AssistChip(
                        modifier = Modifier.height(24.dp),
                        onClick = { /* Ignored */ },
                        border = AssistChipDefaults.assistChipBorder(borderWidth = 1.5.dp),
                        label = {
                            Text(
                                text = schedule.time,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    )
                }
                if (schedule.durationMin != 0) {
                    AssistChip(
                        modifier = Modifier.height(24.dp),
                        onClick = { /* Ignored */ },
                        border = AssistChipDefaults.assistChipBorder(borderWidth = 1.5.dp),
                        label = {
                            Text(
                                text = stringResource(
                                    id = R.string.duration_minutes,
                                    schedule.durationMin
                                ),
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun Rating(schedule: ScheduleItem, modifier: Modifier = Modifier) {
    schedule.rating?.let {
        Card(
            modifier = Modifier
                .padding(start = 60.dp)
                .then(modifier),
            colors = CardDefaults.cardColors(containerColor = Gold)
        ) {
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally)
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
                Text(
                    modifier = Modifier.padding(end = 4.dp),
                    text = stringResource(id = R.string.rating_out_of_10, it.toString()),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
private fun SeasonAndEpisode(schedule: ScheduleItem, modifier: Modifier = Modifier) {
    if (schedule.season != 0 && schedule.episode != 0) {
        ElevatedAssistChip(
            modifier = Modifier
                .padding(top = 4.dp)
                .height(28.dp)
                .then(modifier),
            onClick = { /* Ignored */ },
            border = AssistChipDefaults.assistChipBorder(borderWidth = 1.5.dp),
            label = {
                Text(
                    text = stringResource(
                        id = R.string.season_episode,
                        schedule.season,
                        schedule.episode
                    ),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        )
    }
}

@Composable
private fun ImageCard(coverUrl: String) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .width(120.dp)
            .padding(bottom = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 16.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(coverUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Image(
                    modifier = Modifier.padding(16.dp),
                    painter = painterResource(id = R.drawable.image_placeholder),
                    contentDescription = null
                )
            } else {
                SubcomposeAsyncImageContent()
            }
        }
    }
}
