package com.example.tvschedule.data.show_details.api

import com.example.tvschedule.data.search.api.model.ShowResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface ShowDetailsApi {

    @GET("/shows/{showId}")
    suspend fun getShowDetails(@Path("showId") showId: Long): ShowResponse
}
