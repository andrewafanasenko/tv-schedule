package com.example.tvschedule.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tvschedule.presentation.bookmarks.BookmarksNavigation
import com.example.tvschedule.presentation.home.HomeNavigation
import com.example.tvschedule.presentation.model.Screen

@Composable
fun MainNavHost(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = androidx.compose.ui.Modifier.padding(innerPadding)
    ) {
        composable(Screen.Home.route) {
            HomeNavigation(navController = navController)
        }
        composable(Screen.Bookmarks.route) {
            BookmarksNavigation(navController = navController)
        }
    }
}
