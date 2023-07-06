package com.example.tvschedule.data.search.mapper

import com.example.tvschedule.data.common.Mapper
import com.example.tvschedule.data.common.toLocalDate
import com.example.tvschedule.data.show_details.api.model.ShowResponse
import com.example.tvschedule.domain.show_details.model.Cast
import com.example.tvschedule.domain.show_details.model.Season
import com.example.tvschedule.domain.show_details.model.Show
import javax.inject.Inject


class ShowMapper @Inject constructor() : Mapper<ShowResponse, Show> {

    override fun map(input: ShowResponse): Show {
        return Show(
            id = input.id,
            showName = input.name.orEmpty(),
            summary = input.summary.orEmpty(),
            coverUrl = input.image?.medium.orEmpty(),
            originalCoverUrl = input.image?.original.orEmpty(),
            rating = input.rating?.average,
            averageRuntime = input.averageRuntime ?: 0,
            genres = input.genres.orEmpty(),
            cast = input.embedded?.cast?.map {
                Cast(
                    id = it.person.id,
                    fullName = it.person.name.orEmpty(),
                    characterName = it.character.name.orEmpty(),
                    self = it.self ?: true,
                    birthday = it.person.birthday.toLocalDate(),
                    deathday = it.person.deathday.toLocalDate(),
                    birthPlace = it.person.country?.name.orEmpty(),
                    imageUrl = it.person.image?.medium.orEmpty()
                )
            }.orEmpty(),
            seasons = input.embedded?.seasons?.map {
                Season(
                    id = it.id,
                    number = it.number ?: 0,
                    name = it.name.orEmpty(),
                    summary = it.summary.orEmpty(),
                    episodeOrder = it.episodeOrder ?: 0,
                    premiereDate = it.premiereDate.toLocalDate(),
                    endDate = it.endDate.toLocalDate(),
                    imageUrl = it.image?.medium.orEmpty()
                )
            }.orEmpty()
        )
    }
}

