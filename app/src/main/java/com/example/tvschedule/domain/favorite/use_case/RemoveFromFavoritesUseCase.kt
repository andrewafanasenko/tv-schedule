package com.example.tvschedule.domain.favorite.use_case

import com.example.tvschedule.di.IoDispatcher
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RemoveFromFavoritesUseCase  @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(showId: Long): Result<Unit> = withContext(ioDispatcher) {
        runCatching {
            favoriteRepository.removeFromFavorite(showId)
        }
    }
}
