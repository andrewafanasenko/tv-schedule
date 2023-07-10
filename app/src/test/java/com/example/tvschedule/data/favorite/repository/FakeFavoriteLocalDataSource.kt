package com.example.tvschedule.data.favorite.repository

import com.example.tvschedule.data.favorite.source.local.FavoriteLocalDataSource
import com.example.tvschedule.data.favorite.source.local.db.model.ShowEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFavoriteLocalDataSource : FavoriteLocalDataSource {

    private val favoriteShows = mutableListOf<ShowEntity>()

    override fun favoriteShows(): Flow<List<ShowEntity>> {
        return flowOf(favoriteShows)
    }

    override suspend fun addToFavorite(show: ShowEntity) {
        favoriteShows.add(show)
    }

    override suspend fun removeFromFavorite(showId: Long) {
        favoriteShows.removeIf { it.id == showId }
    }

    override suspend fun updateFavorite(show: ShowEntity) {
        favoriteShows.replaceAll { if (it.id == show.id) show else it }
    }

    override suspend fun getShow(showId: Long): ShowEntity? {
        return favoriteShows.firstOrNull { it.id == showId }
    }
}
