package com.example.tvschedule.data.favorite.mapper

import com.example.tvschedule.data.common.Mapper
import com.example.tvschedule.data.favorite.source.local.db.model.ShowEntity
import com.example.tvschedule.domain.show_details.model.Show
import javax.inject.Inject


class EntityToShowMapper @Inject constructor(): Mapper<ShowEntity, Show> {

    override fun map(input: ShowEntity) = Show(
        id = input.id,
        showName = input.name,
        summary = input.summary,
        coverUrl = input.coverUrl,
        originalCoverUrl = input.originalCoverUrl,
        rating = input.rating,
        averageRuntime = input.averageRuntime,
        genres = input.genres,
        cast = emptyList()
    )
}
