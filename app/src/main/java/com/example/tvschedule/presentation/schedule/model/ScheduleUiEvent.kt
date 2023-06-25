package com.example.tvschedule.presentation.schedule.model

import com.example.tvschedule.presentation.common.ViewEvent
import java.time.LocalDate


sealed interface ScheduleUiEvent : ViewEvent {
    data class SelectDate(val date: LocalDate) : ScheduleUiEvent
}
