package com.example.tvschedule.domain.show_details.use_case

import com.example.tvschedule.di.IoDispatcher
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.example.tvschedule.domain.schedule.repository.ScheduleRepository
import com.example.tvschedule.domain.search.model.Show
import com.example.tvschedule.domain.search.repository.SearchRepository
import com.example.tvschedule.domain.show_details.repository.ShowDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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
    ): Result<Show> = withContext(ioDispatcher) {
        runCatching {
            scheduleRepository.getShowFromCache(showId)?.let {
                return@runCatching it
            }
            searchRepository.getShowFromCache(showId)?.let {
                return@runCatching it
            }
            favoriteRepository.getFavoriteShow(showId)?.let {
                return@runCatching it
            }
            showDetailsRepository.getShowDetails(showId)
        }
    }
}
