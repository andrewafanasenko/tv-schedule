package com.example.tvschedule.data.favorite.repository

import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.example.tvschedule.domain.search.model.Show
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FavoriteRepositoryImpl @Inject constructor(): FavoriteRepository {

    override fun favoriteShows(): Flow<List<Show>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorite(show: Show) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavorite(showId: Long) {
        TODO("Not yet implemented")
    }
}
