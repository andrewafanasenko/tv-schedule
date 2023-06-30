package com.example.tvschedule.domain.search.model

data class Show(
    val id: Long,
    val showName: String,
    val summary: String,
    val coverUrl: String,
    val rating: Double?,
    val averageRuntime: Int
)
