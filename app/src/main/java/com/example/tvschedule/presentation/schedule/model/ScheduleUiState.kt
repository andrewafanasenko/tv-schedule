package com.example.tvschedule.presentation.schedule.model

import com.example.tvschedule.presentation.common.ViewState
import java.time.LocalDate


data class ScheduleUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now(),
    val schedule: List<ScheduleItem> = emptyList()
) : ViewState
