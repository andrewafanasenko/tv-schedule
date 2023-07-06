package com.example.tvschedule.domain.show_details.model


data class Show(
    val id: Long,
    val showName: String,
    val summary: String,
    val coverUrl: String,
    val originalCoverUrl: String,
    val rating: Double?,
    val averageRuntime: Int,
    val genres: List<String>,
    val cast: List<Cast>,
    val seasons: List<Season>
)
