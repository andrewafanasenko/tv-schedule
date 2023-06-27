package com.example.tvschedule.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowResponse(
    val id: Long,
    val url: String?,
    val name: String?,
    val type: String?,
    val language: String?,
    val genres: List<String>?,
    val status: String?,
    val runtime: Int?,
    val averageRuntime: Int?,
    val premiered: String?,
    val officialSite: String? ,
    @SerialName("schedule") val timetable: TimetableResponse?,
    val rating: RatingResponse?,
    val weight: Int?,
    val network: NetworkResponse?,
    val webChannel: NetworkResponse?,
    val dvdCountry: CountryResponse?,
    val externals: ExternalsResponse?,
    val image: ImageResponse?,
    val summary: String?,
    val updated: Long?,
)
