package com.example.tvschedule.presentation.schedule

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.tvschedule.presentation.model.Screen
import com.example.tvschedule.presentation.schedule.model.ScheduleNavCallback

@Composable
fun ScheduleNavigation(navController: NavHostController) {
    ScheduleScreen { navigation ->
        when (navigation) {
            is ScheduleNavCallback.ShowDetails -> {
                navController.navigate(Screen.ShowDetails.createShowRoute(navigation.showId))
            }
        }
    }
}
