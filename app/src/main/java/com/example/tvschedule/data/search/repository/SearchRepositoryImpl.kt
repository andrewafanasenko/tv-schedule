package com.example.tvschedule.data.search.repository

import com.example.tvschedule.data.search.mapper.ShowMapper
import com.example.tvschedule.data.search.source.local.SearchLocalDataSource
import com.example.tvschedule.data.search.source.remote.SearchRemoteDataSource
import com.example.tvschedule.domain.show_details.model.Show
import com.example.tvschedule.domain.search.repository.SearchRepository
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val localDataSource: SearchLocalDataSource,
    private val remoteDataSource: SearchRemoteDataSource,
    private val mapper: ShowMapper
): SearchRepository {

    override suspend fun searchShow(query: String): List<Show> {
        val shows = remoteDataSource.searchShow(query).mapNotNull { it.show }
        shows.forEach {
            localDataSource.shows[it.id] = it
        }
        return mapper.mapList(shows)
    }

    override suspend fun getShowFromCache(showId: Long): Show? {
        return localDataSource.shows[showId]?.let { mapper.map(it) }
    }

}
