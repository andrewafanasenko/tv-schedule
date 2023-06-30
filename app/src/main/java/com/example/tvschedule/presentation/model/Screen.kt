package com.example.tvschedule.presentation.model

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Search: Screen("search")
    object Bookmarks: Screen("bookmarks")
}
