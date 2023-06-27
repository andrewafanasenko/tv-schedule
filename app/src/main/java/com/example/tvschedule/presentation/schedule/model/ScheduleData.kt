package com.example.tvschedule.presentation.schedule.model

import com.example.tvschedule.domain.schedule.model.Schedule
import java.time.LocalDate


data class ScheduleData(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now(),
    val schedule: List<Schedule> = emptyList()
)
