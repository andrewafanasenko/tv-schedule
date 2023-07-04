package com.example.tvschedule.presentation.show_details

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.tvschedule.presentation.show_details.model.ShowDetailsNavCallback


@Composable
fun ShowDetailsNavigation(navController: NavHostController) {
    ShowDetailsScreen { navigation ->
        when (navigation) {
            ShowDetailsNavCallback.Back -> {
                navController.popBackStack()
            }
        }
    }
}
