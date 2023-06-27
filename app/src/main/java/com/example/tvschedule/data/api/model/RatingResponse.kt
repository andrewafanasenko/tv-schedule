package com.example.tvschedule.data.api.model

import kotlinx.serialization.Serializable


@Serializable
data class RatingResponse(
    val average: Double?
)
