package com.example.tvschedule.presentation.schedule.model

import com.example.tvschedule.presentation.common.ViewState


data class ScheduleUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val schedule: List<String> = emptyList()
) : ViewState
