package com.example.tvschedule.data.show_details.api.model

import com.example.tvschedule.data.schedule.api.model.ImageResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SeasonResponse(
    @SerialName("id") val id: Long,
    @SerialName("url") val url: String?,
    @SerialName("number") val number: Int?,
    @SerialName("name") val name: String?,
    @SerialName("summary") val summary: String?,
    @SerialName("episodeOrder") val episodeOrder: Int?,
    @SerialName("premiereDate") val premiereDate: String?,
    @SerialName("endDate") val endDate: String?,
    @SerialName("network") val network: NetworkResponse?,
    @SerialName("image") val image: ImageResponse?,
)
