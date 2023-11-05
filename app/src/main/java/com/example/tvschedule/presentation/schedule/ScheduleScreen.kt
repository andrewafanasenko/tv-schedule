package com.example.tvschedule.presentation.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.tvschedule.presentation.schedule.model.ScheduleItem
import com.example.tvschedule.presentation.schedule.model.ScheduleNavCallback
import com.example.tvschedule.presentation.schedule.model.ScheduleUiEvent
import com.example.tvschedule.presentation.schedule.model.ScheduleUiState
import com.example.tvschedule.presentation.ui.components.EmptyState
import com.example.tvschedule.presentation.ui.components.ErrorState
import com.example.tvschedule.presentation.ui.components.ItemSchedule
import com.example.tvschedule.presentation.ui.components.LoadingState
import com.jcalendar.library.DayContent
import com.jcalendar.library.DayOfWeekTitleContent
import com.jcalendar.library.JCalendar
import com.jcalendar.library.model.CalendarMode
import com.jcalendar.library.model.Day
import com.jcalendar.library.rememberJCalendarState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel(),
    navigation: (ScheduleNavCallback) -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    ScheduleContent(
        state = state,
        onEvent = { viewModel.setEvent(it) },
        onShowClick = { navigation.invoke(ScheduleNavCallback.ShowDetails(it)) }
    )
}

@Composable
private fun ScheduleContent(
    state: ScheduleUiState,
    onEvent: (ScheduleUiEvent) -> Unit,
    onShowClick: (showId: Long) -> Unit
) {
    val listState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        WeekCalendar(
            selectedDate = state.selectedDate,
            onDayClick = { onEvent.invoke(ScheduleUiEvent.SelectDate(it)) }
        )
        when {
            state.isLoading -> LoadingState()
            state.isError -> ErrorState { onEvent.invoke(ScheduleUiEvent.Retry) }
            state.schedule.isEmpty() -> EmptyState()
            else -> Schedule(
                schedule = state.schedule,
                listState = listState,
                onShowClick = onShowClick
            )
        }
    }
}

@Composable
private fun WeekCalendar(
    selectedDate: LocalDate,
    onDayClick: (LocalDate) -> Unit
) {
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
                                MaterialTheme.colorScheme.primary
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
                                        color = MaterialTheme.colorScheme.primary
                                    ),
                                    shape = MaterialTheme.shapes.medium
                                )
                            } else {
                                Modifier
                            }
                        ),
                    defaultTextColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
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
            }
        )
    }
}

@Composable
private fun Schedule(
    schedule: ImmutableList<ScheduleItem>,
    listState: LazyListState,
    onShowClick: (showId: Long) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = schedule,
            key = { it.id }
        ) { item ->
            ItemSchedule(
                schedule = item,
                modifier = Modifier.clickable {
                    onShowClick.invoke(item.showId)
                }
            )
        }
    }
}
