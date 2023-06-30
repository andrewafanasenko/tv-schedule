package com.example.tvschedule.data.search.mapper

import com.example.tvschedule.data.common.Mapper
import com.example.tvschedule.data.search.api.model.ShowResponse
import com.example.tvschedule.domain.search.model.Show
import javax.inject.Inject


class ShowMapper @Inject constructor() : Mapper<ShowResponse, Show> {

    override fun map(input: ShowResponse): Show {
        return Show(
            id = input.id,
            showName = input.name.orEmpty(),
            summary = input.summary.orEmpty(),
            coverUrl = input.image?.medium.orEmpty(),
            rating = input.rating?.average,
            averageRuntime = input.averageRuntime ?: 0
        )
    }
}

