package com.example.tvschedule.data.util

import com.example.tvschedule.data.common.toLocalDateTime
import com.example.tvschedule.data.favorite.source.local.db.model.ShowEntity
import com.example.tvschedule.data.schedule.api.model.ImageResponse
import com.example.tvschedule.data.schedule.api.model.RatingResponse
import com.example.tvschedule.data.schedule.api.model.ScheduleResponse
import com.example.tvschedule.data.search.api.model.ShowSearchResponse
import com.example.tvschedule.data.show_details.api.model.CastResponse
import com.example.tvschedule.data.show_details.api.model.CharacterResponse
import com.example.tvschedule.data.show_details.api.model.CountryResponse
import com.example.tvschedule.data.show_details.api.model.EmbeddedResponse
import com.example.tvschedule.data.show_details.api.model.PersonResponse
import com.example.tvschedule.data.show_details.api.model.SeasonResponse
import com.example.tvschedule.data.show_details.api.model.ShowResponse
import com.example.tvschedule.domain.schedule.model.Schedule
import com.example.tvschedule.domain.show_details.model.Cast
import com.example.tvschedule.domain.show_details.model.Season
import com.example.tvschedule.domain.show_details.model.Show
import com.example.tvschedule.domain.show_details.model.ShowDetails
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

    const val searchQuery = "query"

    const val castId = 43L
    const val castFullName = "John Gold"
    const val castCharacterName = "Peter"
    const val castSelf = false
    const val castBirthdayString = "1967-02-03"
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
    const val seasonPremiereDateString = "2021-08-13"
    val seasonPremiereDate = LocalDate.of(2021, 8, 13)
    const val seasonEndDateString = "2022-01-01"
    val seasonEndDate = LocalDate.of(2022, 1, 1)
    const val seasonImageUrl = "https://image_season.png"

    val imageResponse = ImageResponse(
        medium = showCoverUrl,
        original = showOriginalCoverUrl
    )

    val imageResponseCast = ImageResponse(
        medium = castImageUrl,
        original = castImageUrl
    )

    val imageResponseSeason = ImageResponse(
        medium = seasonImageUrl,
        original = seasonImageUrl
    )

    val seasonResponse = SeasonResponse(
        id = seasonId,
        url = null,
        number = seasonNumber,
        name = seasonName,
        summary = seasonSummary,
        episodeOrder = seasonEpisodeOrder,
        premiereDate = seasonPremiereDateString,
        endDate = seasonEndDateString,
        network = null,
        image = imageResponseSeason
    )

    val seasonsResponse = listOf(seasonResponse, seasonResponse.copy(id = 989))

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

    val ratingResponse = RatingResponse(
        average = showRating
    )

    val countryResponse = CountryResponse(
        name = birthPlace,
        code = null,
        timezone = null
    )

    val personResponse = PersonResponse(
        id = castId,
        url = castImageUrl,
        name = castFullName,
        country = countryResponse,
        birthday = castBirthdayString,
        deathday = null,
        gender = null,
        image = imageResponseCast,
        updated = null
    )

    val characterResponse = CharacterResponse(
        id = null,
        url = null,
        name = castCharacterName,
        image = null
    )

    val castResponse = CastResponse(
        person = personResponse,
        character = characterResponse,
        self = castSelf,
        voice = false
    )

    val castsResponse = listOf(
        castResponse,
        castResponse.copy(
            person = castResponse.person.copy(id = 12)
        )
    )

    val embeddedResponse = EmbeddedResponse(
        cast = castsResponse,
        seasons = seasonsResponse
    )

    val showResponse = ShowResponse(
        id = showId,
        url = null,
        name = showName,
        type = null,
        language = null,
        genres = showGenres,
        status = null,
        runtime = null,
        averageRuntime = showAverageRuntime,
        premiered = null,
        officialSite = null,
        timetable = null,
        rating = ratingResponse,
        weight = null,
        network = null,
        webChannel = null,
        dvdCountry = null,
        externals = null,
        image = imageResponse,
        summary = showSummary,
        updated = null,
        embedded = embeddedResponse
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

    val showDetails = ShowDetails(
        show = show,
        isFavorite = true
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

    const val scheduleId = 235235L
    const val scheduleEpisodeName = "Episode name"
    const val scheduleSummary = "Schedule summary"
    const val scheduleCoverUrl = "https://image_schedule.png"
    const val scheduleSeasonNumber = 2
    const val scheduleEpisodeNumber = 3
    const val scheduleRuntime = 30
    const val scheduleRating = 8.8
    const val scheduleAirStamp = "2024-05-05T05:30:00"

    val imageResponseSchedule = ImageResponse(
        medium = scheduleCoverUrl,
        original = scheduleCoverUrl
    )

    val scheduleRatingResponse = RatingResponse(
        average = scheduleRating
    )

    val scheduleResponse = ScheduleResponse(
        id = scheduleId,
        url = null,
        name = scheduleEpisodeName,
        season = scheduleSeasonNumber,
        number = scheduleEpisodeNumber,
        type = null,
        airdate = null,
        airtime = null,
        airstamp = scheduleAirStamp,
        runtime = scheduleRuntime,
        rating = scheduleRatingResponse,
        image = imageResponseSchedule,
        summary = scheduleSummary,
        show = showResponse
    )

    val schedulesResponse = listOf(scheduleResponse, scheduleResponse.copy(id = 433))

    val schedule = Schedule(
        id = scheduleId,
        episodeName = scheduleEpisodeName,
        summary = scheduleSummary,
        coverUrl = scheduleCoverUrl,
        seasonNumber = scheduleSeasonNumber,
        episodeNumber = scheduleEpisodeNumber,
        runtime = scheduleRuntime,
        airDateTime = scheduleAirStamp.toLocalDateTime(),
        rating = scheduleRating,
        show = show
    )

    val schedules = listOf(schedule, schedule.copy(id = 433))

    val scheduleDate = LocalDate.of(2023, 11, 12)

    val showSearchResponse = ShowSearchResponse(
        score = 9.0,
        show = showResponse
    )

    val showsSearchResponse = listOf(
        showSearchResponse,
        showSearchResponse.copy(
            score = 8.1,
            show = showResponse.copy(id = 12312)
        )
    )
    val shows = listOf(
        show,
        show.copy(id = 12312)
    )
}
