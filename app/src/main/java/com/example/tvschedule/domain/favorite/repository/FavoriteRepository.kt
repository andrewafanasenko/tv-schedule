package com.example.tvschedule.domain.favorite.repository

import com.example.tvschedule.domain.search.model.Show
import kotlinx.coroutines.flow.Flow


interface FavoriteRepository {

    fun favoriteShows(): Flow<List<Show>>

    suspend fun addToFavorite(show: Show)

    suspend fun removeFromFavorite(showId: Long)

    suspend fun updateFavorite(show: Show)

    suspend fun getFavoriteShow(showId: Long): Show?
}
