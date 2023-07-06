package com.example.tvschedule.domain.favorite.use_case

import com.example.tvschedule.di.IoDispatcher
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.example.tvschedule.domain.show_details.model.Show
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GetFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Result<Flow<List<Show>>> = withContext(ioDispatcher) {
        runCatching {
            favoriteRepository.favoriteShows()
        }
    }
}
