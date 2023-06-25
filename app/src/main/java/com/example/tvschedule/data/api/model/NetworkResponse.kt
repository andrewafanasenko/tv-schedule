package com.example.tvschedule.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NetworkResponse(
    @SerialName("days") val id: Long,
    @SerialName("name") val name: String?,
    @SerialName("country") val country: CountryResponse?,
    @SerialName("officialSite") val officialSite: String?
)
