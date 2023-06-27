package com.example.tvschedule.data.api.model

import kotlinx.serialization.Serializable


@Serializable
data class CountryResponse(
    val name: String?,
    val code: String?,
    val timezone: String?
)
