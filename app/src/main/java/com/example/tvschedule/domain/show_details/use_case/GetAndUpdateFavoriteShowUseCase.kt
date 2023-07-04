package com.example.tvschedule.domain.show_details.use_case

import com.example.tvschedule.di.IoDispatcher
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.example.tvschedule.domain.show_details.repository.ShowDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GetAndUpdateFavoriteShowUseCase @Inject constructor(
    private val showDetailsRepository: ShowDetailsRepository,
    private val favoriteRepository: FavoriteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        showId: Long
    ): Result<Unit> = withContext(ioDispatcher) {
        runCatching {
            val show = showDetailsRepository.getShowDetails(showId)
            favoriteRepository.updateFavorite(show)
        }
    }
}
