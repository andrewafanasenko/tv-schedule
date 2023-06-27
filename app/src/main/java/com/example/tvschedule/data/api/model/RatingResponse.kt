package com.example.tvschedule.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RatingResponse(
    @SerialName("average") val average: Double?
)
