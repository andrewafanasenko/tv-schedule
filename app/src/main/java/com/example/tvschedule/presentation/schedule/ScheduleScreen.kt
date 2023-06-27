package com.example.tvschedule.presentation.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tvschedule.presentation.schedule.model.ScheduleUiEvent
import com.example.tvschedule.presentation.schedule.model.ScheduleUiState
import com.example.tvschedule.presentation.ui.components.ProgressBar
import com.jcalendar.library.DayContent
import com.jcalendar.library.DayOfWeekTitleContent
import com.jcalendar.library.JCalendar
import com.jcalendar.library.model.CalendarMode
import com.jcalendar.library.model.Day
import com.jcalendar.library.rememberJCalendarState
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    ScheduleContent(
        state = state,
        onEvent = { viewModel.setEvent(it) }
    )
}

@Composable
private fun ScheduleContent(
    state: ScheduleUiState,
    onEvent: (ScheduleUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        WeekCalendar(
            selectedDate = state.selectedDate,
            onDayClick = {
                onEvent.invoke(ScheduleUiEvent.SelectDate(it))
            }
        )
        if (state.isLoading) {
            ProgressBar()
        } else if (state.isError) {
            // TODO
        } else {
            Schedule(state.schedule)
        }
    }
}

@Composable
private fun WeekCalendar(
    selectedDate: LocalDate,
    onDayClick: (LocalDate) -> Unit
) {
    //var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val calendarState = rememberJCalendarState(
        startMonth = YearMonth.now(),
        endMonth = YearMonth.now().plusMonths(1),
        selectedDate = selectedDate,
        mode = CalendarMode.WEEK
    )
    LaunchedEffect(calendarState) {
        snapshotFlow { calendarState.selectedDate }
            .distinctUntilChanged()
            .collect {
                onDayClick.invoke(it)
                //   selectedDate = it
            }
    }
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = selectedDate.format(DateTimeFormatter.ofPattern("d MMMM, yyyy")),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black)
        )
        JCalendar(
            modifier = Modifier.padding(bottom = 8.dp),
            calendarState = calendarState,
            dayContent = { day: Day ->
                DayContent(
                    day = day,
                    modifier = Modifier
                        .background(
                            color = if (day.isSelected) {
                                MaterialTheme.colorScheme.secondary
                            } else {
                                MaterialTheme.colorScheme.surface
                            },
                            shape = MaterialTheme.shapes.medium
                        )
                        .then(
                            if (day.date == LocalDate.now() && day.isSelected.not()) {
                                Modifier.border(
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.secondary
                                    ),
                                    shape = MaterialTheme.shapes.medium
                                )
                            } else {
                                Modifier
                            }
                        ),
                    defaultTextColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    textStyle = MaterialTheme.typography.titleMedium,
                    size = 48.dp,
                    onClick = {
                        calendarState.selectDay(day)
                    }
                )
            },
            dayOfWeekTitleContent = { dayOfWeek: DayOfWeek ->
                DayOfWeekTitleContent(
                    dayOfWeek = dayOfWeek,
                    modifier = Modifier.padding(bottom = 8.dp),
                    dayOfWeekStyle = java.time.format.TextStyle.SHORT,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    textStyle = MaterialTheme.typography.labelMedium
                )
            },
        )
    }
}

@Composable
private fun Schedule(schedule: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(schedule.count()) {
            Text(text = schedule[it])
        }
    }
}
