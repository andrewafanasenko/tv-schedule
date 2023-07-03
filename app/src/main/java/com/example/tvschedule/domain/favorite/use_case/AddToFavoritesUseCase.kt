package com.example.tvschedule.domain.favorite.use_case

import com.example.tvschedule.di.IoDispatcher
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.example.tvschedule.domain.search.model.Show
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AddToFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(show: Show): Result<Unit> = withContext(ioDispatcher) {
        runCatching {
            favoriteRepository.addToFavorite(show)
        }
    }
}
