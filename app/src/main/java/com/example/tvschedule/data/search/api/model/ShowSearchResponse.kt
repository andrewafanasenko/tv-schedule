package com.example.tvschedule.data.search.api.model

import com.example.tvschedule.data.show_details.api.model.ShowResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowSearchResponse(
    @SerialName("score") val score: Double?,
    @SerialName("show") val show: ShowResponse?
)
