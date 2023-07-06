package com.example.tvschedule.presentation.ui.components

import android.text.Html
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tvschedule.R
import com.example.tvschedule.presentation.show_details.model.SeasonItem


@Composable
fun ItemSeason(season: SeasonItem, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(modifier),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier.fillMaxSize()) {
            ImageCard(
                coverUrl = season.imageUrl,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp),
                elevation = 0.dp
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = season.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                if (season.dateRange.isNotBlank()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = season.dateRange,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                if (season.episodesCount > 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.episodes),
                            style = MaterialTheme.typography.titleSmall,
                        )
                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = season.episodesCount.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = Html.fromHtml(season.summary, Html.FROM_HTML_MODE_LEGACY).toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
