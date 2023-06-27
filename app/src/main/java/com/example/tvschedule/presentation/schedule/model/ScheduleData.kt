package com.example.tvschedule.presentation.schedule.model

import com.example.tvschedule.domain.schedule.model.Schedule


data class ScheduleData(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val schedule: List<Schedule> = emptyList()
)
