package com.example.tvschedule.presentation.schedule.model

import com.example.tvschedule.presentation.common.ViewState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

data class ScheduleUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now(),
    val schedule: ImmutableList<ScheduleItem> = persistentListOf()
) : ViewState
