package com.example.tvschedule.domain.schedule.model

import com.example.tvschedule.domain.search.model.Show
import java.time.LocalDateTime

data class Schedule(
    val id: Long,
    val episodeName: String,
    val summary: String,
    val coverUrl: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val runtime: Int,
    val airDateTime: LocalDateTime?,
    val rating: Double?,
    val show: Show?
)
