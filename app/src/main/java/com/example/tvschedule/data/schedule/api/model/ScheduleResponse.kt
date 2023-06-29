package com.example.tvschedule.data.schedule.api.model

import com.example.tvschedule.data.search.api.model.ShowResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleResponse(
    @SerialName("id") val id: Long,
    @SerialName("url") val url: String?,
    @SerialName("name") val name: String?,
    @SerialName("season") val season: Int?,
    @SerialName("number") val number: Int?,
    @SerialName("type") val type: String?,
    @SerialName("airdate") val airdate: String?,
    @SerialName("airtime") val airtime: String?,
    @SerialName("airstamp") val airstamp: String?,
    @SerialName("runtime") val runtime: Int?,
    @SerialName("rating") val rating: RatingResponse?,
    @SerialName("image") val image: ImageResponse?,
    @SerialName("summary") val summary: String?,
    @SerialName("show") val show: ShowResponse?,
)
