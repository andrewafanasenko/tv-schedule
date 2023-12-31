package com.example.tvschedule.data.show_details.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TimetableResponse(
    @SerialName("time") val time: String?,
    @SerialName("days") val days: List<String>?
)
