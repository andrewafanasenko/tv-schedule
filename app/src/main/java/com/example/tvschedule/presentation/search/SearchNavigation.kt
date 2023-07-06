package com.example.tvschedule.presentation.search

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.tvschedule.presentation.model.Screen
import com.example.tvschedule.presentation.search.model.SearchNavCallback


@Composable
fun SearchNavigation(navController: NavHostController) {
    SearchScreen { navigation ->
        when (navigation) {
            is SearchNavCallback.ShowDetails -> {
                navController.navigate(Screen.ShowDetails.createShowRoute(navigation.showId))
            }
        }
    }
}
