package com.example.tvschedule.data.favorite.repository

import com.example.tvschedule.data.favorite.mapper.EntityToShowMapper
import com.example.tvschedule.data.favorite.mapper.ShowToEntityMapper
import com.example.tvschedule.data.favorite.source.local.FavoriteLocalDataSourceImpl
import com.example.tvschedule.data.util.ModelUtil.show
import com.example.tvschedule.data.util.ModelUtil.showEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class FavoriteRepositoryTest {

    private val localDataSource = mock<FavoriteLocalDataSourceImpl>()

    private val entityToShowMapper = mock<EntityToShowMapper>()

    private val showToEntityMapper = mock<ShowToEntityMapper>()

    private val favoriteRepository = FavoriteRepositoryImpl(
        localDataSource = localDataSource,
        entityToShowMapper = entityToShowMapper,
        showToEntityMapper = showToEntityMapper,
    )

    @Test
    fun `get favorite shows success`() = runTest {
        val showEntities = listOf(showEntity)
        val shows = listOf(show)
        val flow = flow { emit(showEntities) }
        whenever(entityToShowMapper.map(showEntity)).thenReturn(show)
        whenever(localDataSource.favoriteShows()).thenReturn(flow)
        favoriteRepository.favoriteShows().collect { result ->
            verify(localDataSource, times(1)).favoriteShows()
            verify(entityToShowMapper, times(1)).map(showEntity)
            assertThat(result).isEqualTo(shows)
        }
    }

    @Test
    fun `get favorite shows failed`() = runTest {
        val exception = IllegalStateException("Failed to get favorites")
        whenever(localDataSource.favoriteShows()).thenThrow(exception)
        runCatching {
            favoriteRepository.favoriteShows()
        }.onFailure { e ->
            verify(localDataSource, times(1)).favoriteShows()
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `add to favorites success`() = runTest {
        whenever(showToEntityMapper.map(show)).thenReturn(showEntity)
        whenever(localDataSource.addToFavorite(showEntity)).thenReturn(Unit)
        val result = favoriteRepository.addToFavorite(show)
        verify(localDataSource, times(1)).addToFavorite(showEntity)
        verify(showToEntityMapper, times(1)).map(show)
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun `add to favorites failed`() = runTest {
        whenever(showToEntityMapper.map(show)).thenReturn(showEntity)
        val exception = IllegalStateException("Failed to add to favorite")
        whenever(localDataSource.addToFavorite(showEntity)).thenThrow(exception)
        runCatching {
            favoriteRepository.addToFavorite(show)
        }.onFailure { e ->
            verify(localDataSource, times(1)).addToFavorite(showEntity)
            verify(showToEntityMapper, times(1)).map(show)
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `remove from favorites success`() = runTest {
        whenever(localDataSource.removeFromFavorite(show.id)).thenReturn(Unit)
        val result = favoriteRepository.removeFromFavorite(show.id)
        verify(localDataSource, times(1)).removeFromFavorite(show.id)
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun `remove from favorites failed`() = runTest {
        val exception = IllegalStateException("Failed to remove from favorite")
        whenever(localDataSource.removeFromFavorite(show.id)).thenThrow(exception)
        runCatching {
            favoriteRepository.removeFromFavorite(show.id)
        }.onFailure { e ->
            verify(localDataSource, times(1)).removeFromFavorite(show.id)
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `update favorite success`() = runTest {
        whenever(showToEntityMapper.map(show)).thenReturn(showEntity)
        whenever(localDataSource.updateFavorite(showEntity)).thenReturn(Unit)
        val result = favoriteRepository.updateFavorite(show)
        verify(localDataSource, times(1)).updateFavorite(showEntity)
        verify(showToEntityMapper, times(1)).map(show)
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun `update favorite failed`() = runTest {
        whenever(showToEntityMapper.map(show)).thenReturn(showEntity)
        val exception = IllegalStateException("Failed to update favorite")
        whenever(localDataSource.updateFavorite(showEntity)).thenThrow(exception)
        runCatching {
            favoriteRepository.updateFavorite(show)
        }.onFailure { e ->
            verify(localDataSource, times(1)).updateFavorite(showEntity)
            verify(showToEntityMapper, times(1)).map(show)
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `get favorite success`() = runTest {
        whenever(entityToShowMapper.map(showEntity)).thenReturn(show)
        whenever(localDataSource.getShow(show.id)).thenReturn(showEntity)
        val result = favoriteRepository.getFavoriteShow(show.id)
        verify(localDataSource, times(1)).getShow(show.id)
        verify(entityToShowMapper, times(1)).map(showEntity)
        assertThat(result).isEqualTo(show)
    }

    @Test
    fun `get favorite failed`() = runTest {
        val exception = IllegalStateException("Failed to get favorite")
        whenever(localDataSource.getShow(show.id)).thenThrow(exception)
        runCatching {
            favoriteRepository.getFavoriteShow(show.id)
        }.onFailure { e ->
            verify(localDataSource, times(1)).getShow(show.id)
            assertThat(e).isEqualTo(exception)
        }
    }
}
