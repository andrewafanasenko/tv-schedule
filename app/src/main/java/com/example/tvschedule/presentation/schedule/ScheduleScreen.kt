package com.example.tvschedule.presentation.schedule

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel = hiltViewModel()) {
    Text(text = "Schedule")
}
