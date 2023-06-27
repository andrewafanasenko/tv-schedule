package com.example.tvschedule.presentation.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tvschedule.presentation.schedule.model.ScheduleUiState
import com.example.tvschedule.presentation.ui.components.ProgressBar
import com.jcalendar.library.JCalendar
import com.jcalendar.library.model.CalendarMode
import com.jcalendar.library.rememberJCalendarState
import java.time.YearMonth

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    ScheduleContent(state)
}

@Composable
private fun ScheduleContent(state: ScheduleUiState) {
    Column(modifier = Modifier.fillMaxSize()) {
        WeekCalendar()
        if (state.isLoading) {
            ProgressBar()
        } else if (state.isError) {
            
        } else {
            Schedule(state.schedule)
        }
    }
}

@Composable
private fun WeekCalendar() {
    val calendarState = rememberJCalendarState(
        startMonth = YearMonth.now(),
        endMonth = YearMonth.now().plusMonths(1),
        mode = CalendarMode.WEEK
    )
    JCalendar(calendarState = calendarState)
}

@Composable
private fun Schedule(schedule: List<String>) {
    LazyColumn() {
        items(schedule.count()) {
            Text(text = schedule[it])
        }
    }
}
