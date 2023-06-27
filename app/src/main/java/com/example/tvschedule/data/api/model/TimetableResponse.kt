package com.example.tvschedule.data.api.model

import kotlinx.serialization.Serializable


@Serializable
data class TimetableResponse(
    val time: String?,
    val days: List<String>?
)
