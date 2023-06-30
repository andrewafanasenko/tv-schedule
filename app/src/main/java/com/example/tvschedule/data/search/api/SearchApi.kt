package com.example.tvschedule.data.search.api

import com.example.tvschedule.data.search.api.model.ShowSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchApi {

    @GET("/search/shows")
    suspend fun searchShow(@Query("q") query: String): List<ShowSearchResponse>
}
