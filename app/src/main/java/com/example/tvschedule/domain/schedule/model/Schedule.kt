package com.example.tvschedule.domain.schedule.model

import java.time.LocalDateTime

data class Schedule(
    val id: Long,
    val episodeName: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val runtime: Int,
    val airDateTime: LocalDateTime,
    val show: Show
)
