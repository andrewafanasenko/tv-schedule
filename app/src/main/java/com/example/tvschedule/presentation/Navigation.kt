package com.example.tvschedule.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tvschedule.presentation.favorite.FavoriteNavigation
import com.example.tvschedule.presentation.schedule.ScheduleNavigation
import com.example.tvschedule.presentation.model.Screen
import com.example.tvschedule.presentation.search.SearchNavigation
import com.example.tvschedule.presentation.show_details.ShowDetailsNavigation

@Composable
fun MainNavHost(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Home.route) {
            ScheduleNavigation(navController = navController)
        }
        composable(Screen.Search.route) {
            SearchNavigation(navController = navController)
        }
        composable(Screen.Favorite.route) {
            FavoriteNavigation(navController = navController)
        }
        composable(
            route = Screen.ShowDetails.route,
            arguments = listOf(navArgument(Screen.SHOW_ID) { type = NavType.LongType })
        ) {
            ShowDetailsNavigation(navController = navController)
        }
    }
}
