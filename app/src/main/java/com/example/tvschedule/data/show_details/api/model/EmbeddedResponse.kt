package com.example.tvschedule.data.show_details.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class EmbeddedResponse(
    @SerialName("cast") val cast: List<CastResponse>?,
    @SerialName("seasons") val seasons: List<SeasonResponse>?
)
