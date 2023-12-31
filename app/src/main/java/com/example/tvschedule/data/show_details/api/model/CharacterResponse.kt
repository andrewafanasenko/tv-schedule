package com.example.tvschedule.data.show_details.api.model

import com.example.tvschedule.data.schedule.api.model.ImageResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CharacterResponse(
    @SerialName("id") val id: Long?,
    @SerialName("url") val url: String?,
    @SerialName("name") val name: String?,
    @SerialName("image") val image: ImageResponse?,
)
