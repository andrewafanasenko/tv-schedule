package com.example.tvschedule.data.favorite.repository

import com.example.tvschedule.data.favorite.mapper.EntityToShowMapper
import com.example.tvschedule.data.favorite.mapper.ShowToEntityMapper
import com.example.tvschedule.data.util.ModelUtil.castFullName
import com.example.tvschedule.data.util.ModelUtil.show
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class FavoriteRepositoryTest {

    private lateinit var favoriteRepository: FavoriteRepositoryImpl

    private val localDataSource = FakeFavoriteLocalDataSource()

    private val entityToShowMapper = EntityToShowMapper()

    private val showToEntityMapper = ShowToEntityMapper()

    @Before
    fun setUp() {
        favoriteRepository = FavoriteRepositoryImpl(
            localDataSource = localDataSource,
            entityToShowMapper = entityToShowMapper,
            showToEntityMapper = showToEntityMapper,
        )
        runBlocking {
            favoriteRepository.addToFavorite(show)
        }
    }

    @Test
    fun `favorites exist success`() = runTest {
        assertThat(favoriteRepository.favoriteShows().toList()).isNotEmpty()
        assertThat(favoriteRepository.favoriteShows().first().first())
            .isEqualTo(entityToShowMapper.map(showToEntityMapper.map(show)))
    }

    @Test
    fun `add to favorites success`() = runTest {
        favoriteRepository.addToFavorite(show)
        assertThat(localDataSource.favoriteShows().first())
            .contains(showToEntityMapper.map(show))
    }

    @Test
    fun `remove from favorites success`() = runTest {
        favoriteRepository.removeFromFavorite(show.id)
        assertThat(localDataSource.favoriteShows().first())
            .doesNotContain(showToEntityMapper.map(show))
    }

    @Test
    fun `update favorite success`() = runTest {
        val updatedShow = show.copy(showName = castFullName)
        favoriteRepository.updateFavorite(updatedShow)
        assertThat(localDataSource.favoriteShows().first())
            .contains(showToEntityMapper.map(updatedShow))
    }

    @Test
    fun `get favorite success`() = runTest {
        val favorite = favoriteRepository.getFavoriteShow(show.id)
        assertThat(entityToShowMapper.map(showToEntityMapper.map(show)))
            .isEqualTo(favorite)
    }
}
