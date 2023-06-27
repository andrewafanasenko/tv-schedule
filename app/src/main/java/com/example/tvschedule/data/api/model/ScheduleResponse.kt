package com.example.tvschedule.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleResponse(
    val id: Long,
    val url: String?,
    val name: String?,
    val season: Int?,
    val number: Int?,
    val type: String?,
    val airdate: String?,
    val airtime: String?,
    val airstamp: String?,
    val runtime: Int?,
    val rating: RatingResponse?,
    val image: ImageResponse?,
    val summary: String?,
    val show: ShowResponse?,
)
