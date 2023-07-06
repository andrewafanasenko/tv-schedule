package com.example.tvschedule.presentation.model

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Search: Screen("search")
    object Favorite: Screen("favorite")
    object ShowDetails: Screen("showDetails/{$SHOW_ID}") {

        fun createShowRoute(showId: Long): String = "showDetails/$showId"
    }

    companion object {
        const val SHOW_ID = "showId"
    }
}
