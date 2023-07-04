package com.example.tvschedule.data.favorite.source.local

import com.example.tvschedule.data.favorite.source.local.db.model.ShowEntity
import kotlinx.coroutines.flow.Flow


interface FavoriteLocalDataSource {

    fun favoriteShows(): Flow<List<ShowEntity>>

    suspend fun addToFavorite(show: ShowEntity)

    suspend fun removeFromFavorite(showId: Long)

    suspend fun updateFavorite(show: ShowEntity)

    suspend fun getShow(showId: Long): ShowEntity?

}
