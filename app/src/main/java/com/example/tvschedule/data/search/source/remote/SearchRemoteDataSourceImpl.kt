package com.example.tvschedule.data.search.source.remote

import com.example.tvschedule.data.search.api.SearchApi
import com.example.tvschedule.data.search.api.model.ShowSearchResponse
import javax.inject.Inject


class SearchRemoteDataSourceImpl @Inject constructor(
    private val api: SearchApi
) : SearchRemoteDataSource {

    override suspend fun searchShow(
        query: String
    ): List<ShowSearchResponse> = api.searchShow(query)

}
