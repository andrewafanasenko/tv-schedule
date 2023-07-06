package com.example.tvschedule.domain.show_details.use_case

import com.example.tvschedule.di.IoDispatcher
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.example.tvschedule.domain.schedule.repository.ScheduleRepository
import com.example.tvschedule.domain.search.repository.SearchRepository
import com.example.tvschedule.domain.show_details.model.ShowDetails
import com.example.tvschedule.domain.show_details.repository.ShowDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetShowDetailsUseCase @Inject constructor(
    private val showDetailsRepository: ShowDetailsRepository,
    private val scheduleRepository: ScheduleRepository,
    private val searchRepository: SearchRepository,
    private val favoriteRepository: FavoriteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        showId: Long
    ): Flow<ShowDetails> = flow {
        var isFavorite = false
        favoriteRepository.getFavoriteShow(showId)?.let {
            isFavorite = true
            emit(ShowDetails(it, isFavorite = true))
        }
        scheduleRepository.getShowFromCache(showId)?.let {
            emit(ShowDetails(it, isFavorite = false))
        }
        searchRepository.getShowFromCache(showId)?.let {
            emit(ShowDetails(it, isFavorite = false))
        }
        val showFromRemote = showDetailsRepository.getShowDetails(showId)
        emit(ShowDetails(showFromRemote, isFavorite = isFavorite))
        if (isFavorite) {
            favoriteRepository.updateFavorite(showFromRemote)
        }
    }.flowOn(ioDispatcher)
}
