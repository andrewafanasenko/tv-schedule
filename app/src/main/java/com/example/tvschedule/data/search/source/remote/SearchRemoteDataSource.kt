package com.example.tvschedule.data.search.source.remote

import com.example.tvschedule.data.search.api.model.ShowSearchResponse


interface SearchRemoteDataSource {

    suspend fun searchShow(query: String): List<ShowSearchResponse>

}
