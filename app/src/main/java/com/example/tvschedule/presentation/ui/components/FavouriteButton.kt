package com.example.tvschedule.presentation.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun FavouriteButton(
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
