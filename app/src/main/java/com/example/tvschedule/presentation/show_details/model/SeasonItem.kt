package com.example.tvschedule.presentation.show_details.model


data class SeasonItem(
    val id: Long,
    val name: String,
    val summary: String,
    val episodesCount: Int,
    val dateRange: String,
    val imageUrl: String,
)
