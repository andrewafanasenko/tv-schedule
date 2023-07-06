package com.example.tvschedule.data.show_details.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse(
    @SerialName("person") val person: PersonResponse,
    @SerialName("character") val character: CharacterResponse,
    @SerialName("self") val self: Boolean?,
    @SerialName("voice") val voice: Boolean?,
)
