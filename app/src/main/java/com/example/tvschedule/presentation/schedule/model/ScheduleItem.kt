package com.example.tvschedule.presentation.schedule.model

data class ScheduleItem(
    val id: Long,
    val name: String,
    val episodeName: String,
    val summary: String,
    val coverUrl: String,
    val time: String,
    val durationMin: Int,
    val rating: Double?,
    val season: Int,
    val episode: Int
)
