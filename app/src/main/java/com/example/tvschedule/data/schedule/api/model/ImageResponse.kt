package com.example.tvschedule.data.schedule.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ImageResponse(
    @SerialName("medium") val medium: String?,
    @SerialName("original") val original: String?
)
