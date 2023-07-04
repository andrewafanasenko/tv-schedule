package com.example.tvschedule.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ImageCard(coverUrl: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 16.dp)
    ) {
        ImageWithPlaceholder(imageUrl = coverUrl, modifier = Modifier.fillMaxSize())
    }
}
