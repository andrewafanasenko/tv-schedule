package com.example.tvschedule.domain.show_details.model

import java.time.LocalDate


data class Season(
    val id: Long,
    val number: Int,
    val name: String,
    val summary: String,
    val episodeOrder: Int,
    val premiereDate: LocalDate?,
    val endDate: LocalDate?,
    val imageUrl: String
)
