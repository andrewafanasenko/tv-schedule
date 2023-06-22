package com.example.tvschedule.presentation.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tvschedule.R

sealed class NavigationItem(
    val route: String,
    @StringRes val titleRes: Int,
    val icon: ImageVector
) {
    object Home : NavigationItem(
        route = Screen.Home.route,
        titleRes = R.string.bottom_navigation_home,
        icon = Icons.Default.Home
    )

    object Bookmarks : NavigationItem(
        route = Screen.Bookmarks.route,
        titleRes = R.string.bottom_navigation_bookmarks,
        icon = Icons.Default.Star
    )
}
