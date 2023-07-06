package com.example.tvschedule.data.util

import com.example.tvschedule.data.favorite.source.local.db.model.ShowEntity
import com.example.tvschedule.domain.show_details.model.Cast
import com.example.tvschedule.domain.show_details.model.Season
import com.example.tvschedule.domain.show_details.model.Show
import java.time.LocalDate

object ModelUtil {

    const val showId = 123L
    const val showName = "Name"
    const val showSummary = "Summary"
    const val showCoverUrl = "https://image.png"
    const val showOriginalCoverUrl = "https://image_original.png"
    const val showRating = 8.7
    const val showAverageRuntime = 30
    val showGenres = listOf("Comedy", "Drama", "Thriller")

    const val castId = 43L
    const val castFullName = "John Gold"
    const val castCharacterName = "Peter"
    const val castSelf = false
    val castBirthday = LocalDate.of(1967, 2, 3)
    val castDeathday: LocalDate? = null
    const val birthPlace = "Panama"
    const val castImageUrl = "https://image_cast.png"

    val cast = Cast(
        id = castId,
        fullName = castFullName,
        characterName = castCharacterName,
        self = castSelf,
        birthday = castBirthday,
        deathday = castDeathday,
        birthPlace = birthPlace,
        imageUrl = castImageUrl
    )

    val casts = listOf(cast, cast.copy(id = 12))

    const val seasonId = 413L
    const val seasonNumber = 1
    const val seasonName = "First"
    const val seasonSummary = "Season summary"
    const val seasonEpisodeOrder = 12
    val seasonPremiereDate = LocalDate.of(2021, 8, 13)
    val seasonEndDate = LocalDate.of(2022, 1, 1)
    const val seasonImageUrl = "https://image_season.png"

    val season = Season(
        id = seasonId,
        number = seasonNumber,
        name = seasonName,
        summary = seasonSummary,
        episodeOrder = seasonEpisodeOrder,
        premiereDate = seasonPremiereDate,
        endDate = seasonEndDate,
        imageUrl = seasonImageUrl
    )

    val seasons = listOf(season, season.copy(id = 989))

    val showEntity = ShowEntity(
        id = showId,
        name = showName,
        summary = showSummary,
        coverUrl = showCoverUrl,
        originalCoverUrl = showOriginalCoverUrl,
        rating = showRating,
        averageRuntime = showAverageRuntime,
        genres = showGenres
    )

    val show = Show(
        id = showId,
        showName = showName,
        summary = showSummary,
        coverUrl = showCoverUrl,
        originalCoverUrl = showOriginalCoverUrl,
        rating = showRating,
        averageRuntime = showAverageRuntime,
        genres = showGenres,
        cast = casts,
        seasons = seasons
    )

    val showFromEntity = Show(
        id = showId,
        showName = showName,
        summary = showSummary,
        coverUrl = showCoverUrl,
        originalCoverUrl = showOriginalCoverUrl,
        rating = showRating,
        averageRuntime = showAverageRuntime,
        genres = showGenres,
        cast = emptyList(),
        seasons = emptyList()
    )
}
