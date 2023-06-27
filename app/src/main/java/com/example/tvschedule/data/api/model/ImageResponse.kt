package com.example.tvschedule.data.api.model

import kotlinx.serialization.Serializable


@Serializable
data class ImageResponse(
    val medium: String?,
    val original: String?
)
