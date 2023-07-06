package com.example.tvschedule.data.show_details.repository

import com.example.tvschedule.data.search.mapper.ShowMapper
import com.example.tvschedule.data.show_details.api.ShowDetailsApi
import com.example.tvschedule.domain.show_details.model.Show
import com.example.tvschedule.domain.show_details.repository.ShowDetailsRepository
import javax.inject.Inject


class ShowDetailsRepositoryImpl @Inject constructor(
    private val api: ShowDetailsApi,
    private val showMapper: ShowMapper
) : ShowDetailsRepository {

    override suspend fun getShowDetails(
        showId: Long
    ): Show = showMapper.map(api.getShowDetails(showId))
}
