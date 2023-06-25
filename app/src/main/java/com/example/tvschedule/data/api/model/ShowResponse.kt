package com.example.tvschedule.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowResponse(
    @SerialName("id") val id: Long,
    @SerialName("url") val url: String?,
    @SerialName("name") val name: String?,
    @SerialName("type") val type: String?,
    @SerialName("language") val language: String?,
    @SerialName("genres") val genres: List<String>?,
    @SerialName("status") val status: String?,
    @SerialName("runtime") val runtime: Int?,
    @SerialName("averageRuntime") val averageRuntime: Int?,
    @SerialName("premiered") val premiered: String?,
    @SerialName("officialSite") val officialSite: String? ,
    @SerialName("schedule") val timetable: TimetableResponse?,
    @SerialName("rating") val rating: RatingResponse?,
    @SerialName("weight") val weight: Int?,
    @SerialName("network") val network: NetworkResponse?,
    @SerialName("webChannel") val webChannel: NetworkResponse?,
    @SerialName("dvdCountry") val dvdCountry: CountryResponse?,
    @SerialName("externals") val externals: ExternalsResponse?,
    @SerialName("image") val image: ImageResponse?,
    @SerialName("summary") val summary: String?,
    @SerialName("updated") val updated: Long?,
)
