package com.example.tvschedule.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CountryResponse(
    @SerialName("name") val name: String?,
    @SerialName("code") val code: String?,
    @SerialName("timezone") val timezone: String?
)
