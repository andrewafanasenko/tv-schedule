package com.example.tvschedule.data.favorite.source.local

import com.example.tvschedule.data.favorite.source.local.db.FavoriteShowsDao
import com.example.tvschedule.data.favorite.source.local.db.model.ShowEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FavoriteLocalDataSourceImpl @Inject constructor(
    private val favoriteShowsDao: FavoriteShowsDao
) : FavoriteLocalDataSource {

    override fun favoriteShows(): Flow<List<ShowEntity>> = favoriteShowsDao.showsUpdates()

    override suspend fun addToFavorite(show: ShowEntity) {
        favoriteShowsDao.addShow(show)
    }

    override suspend fun removeFromFavorite(showId: Long) {
        favoriteShowsDao.deleteShow(showId)
    }

    override suspend fun updateFavorite(show: ShowEntity) {
        favoriteShowsDao.updateShow(show)
    }

    override suspend fun getShow(showId: Long): ShowEntity? = favoriteShowsDao.getShow(showId)

}
