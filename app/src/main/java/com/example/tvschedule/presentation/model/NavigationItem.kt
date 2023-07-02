package com.example.tvschedule.presentation.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tvschedule.R

sealed class NavigationItem(
    val route: String,
    @StringRes val titleRes: Int,
    val icon: ImageVector
) {
    object Home : NavigationItem(
        route = Screen.Home.route,
        titleRes = R.string.bottom_navigation_schedule,
        icon = Icons.Default.Home
    )

    object Search : NavigationItem(
        route = Screen.Search.route,
        titleRes = R.string.bottom_navigation_search,
        icon = Icons.Default.Search
    )

    object Favorite : NavigationItem(
        route = Screen.Favorite.route,
        titleRes = R.string.bottom_navigation_favorite,
        icon = Icons.Default.Favorite
    )
}
