package com.example.tvschedule.data.api.model

import kotlinx.serialization.Serializable


@Serializable
data class NetworkResponse(
    val id: Long,
    val name: String?,
    val country: CountryResponse?,
    val officialSite: String?
)
