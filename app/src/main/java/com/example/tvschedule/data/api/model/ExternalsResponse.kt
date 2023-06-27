package com.example.tvschedule.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ExternalsResponse(
    @SerialName("tvrage") val tvrage: Long?,
    @SerialName("thetvdb") val thetvdb: Long?,
    @SerialName("imdb") val imdb: String?
)
