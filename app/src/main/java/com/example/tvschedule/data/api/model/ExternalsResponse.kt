package com.example.tvschedule.data.api.model

import kotlinx.serialization.Serializable


@Serializable
data class ExternalsResponse(
    val tvrage: Long?,
    val thetvdb: Long?,
    val imdb: String?
)
