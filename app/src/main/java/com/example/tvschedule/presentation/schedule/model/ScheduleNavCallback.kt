package com.example.tvschedule.presentation.schedule.model


sealed interface ScheduleNavCallback {
    data class ShowDetails(val showId: Long) : ScheduleNavCallback
}
