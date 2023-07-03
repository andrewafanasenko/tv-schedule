package com.example.tvschedule.data.favorite.repository

import com.example.tvschedule.data.favorite.mapper.EntityToShowMapper
import com.example.tvschedule.data.favorite.mapper.ShowToEntityMapper
import com.example.tvschedule.data.favorite.source.local.FavoriteLocalDataSource
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.example.tvschedule.domain.search.model.Show
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class FavoriteRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteLocalDataSource,
    private val entityToShowMapper: EntityToShowMapper,
    private val showToEntityMapper: ShowToEntityMapper
) : FavoriteRepository {

    override fun favoriteShows(): Flow<List<Show>> = localDataSource.favoriteShows()
        .map { entity ->
            entity.map { entityToShowMapper.map(it) }
        }

    override suspend fun addToFavorite(show: Show) {
        localDataSource.addToFavorite(showToEntityMapper.map(show))
    }

    override suspend fun removeFromFavorite(showId: Long) {
        localDataSource.removeFromFavorite(showId)
    }
}
