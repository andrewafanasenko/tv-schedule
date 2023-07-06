package com.example.tvschedule.presentation.favorite

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.tvschedule.presentation.favorite.model.FavoriteNavCallback
import com.example.tvschedule.presentation.model.Screen

@Composable
fun FavoriteNavigation(navController: NavHostController) {
    FavoriteScreen { navigation ->
        when (navigation) {
            is FavoriteNavCallback.ShowDetails -> {
                navController.navigate(Screen.ShowDetails.createShowRoute(navigation.showId))
            }
        }
    }
}
