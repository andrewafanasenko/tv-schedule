package com.example.tvschedule.data.favorite.source.local

import com.example.tvschedule.data.favorite.source.local.db.FavoriteShowsDao
import com.example.tvschedule.data.util.ModelUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class FavoriteLocalDataSourceTest {

    private val favoriteShowsDao = mock<FavoriteShowsDao>()
    private val favoriteLocalDataSource = FavoriteLocalDataSourceImpl(favoriteShowsDao)

    @Test
    fun `get favorite shows updates success`() = runTest {
        val showEntities = listOf(ModelUtil.showEntity)
        val flow = flow { emit(showEntities) }
        whenever(favoriteShowsDao.showsUpdates()).thenReturn(flow)
        favoriteLocalDataSource.favoriteShows().collect { result ->
            verify(favoriteShowsDao, Mockito.times(1)).showsUpdates()
            assertThat(result).isEqualTo(showEntities)
        }
    }

    @Test
    fun `get favorite shows updates failed`() = runTest {
        val exception = IllegalStateException("Failed to get show updates")
        whenever(favoriteShowsDao.showsUpdates()).thenThrow(exception)
        runCatching {
            favoriteLocalDataSource.favoriteShows()
        }.onFailure { e ->
            verify(favoriteShowsDao, Mockito.times(1)).showsUpdates()
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `add to favorites success`() = runTest {
        whenever(favoriteShowsDao.addShow(ModelUtil.showEntity)).thenReturn(Unit)
        val result = favoriteLocalDataSource.addToFavorite(ModelUtil.showEntity)
        verify(favoriteShowsDao, Mockito.times(1))
            .addShow(ModelUtil.showEntity)
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun `add to favorites failed`() = runTest {
        val exception = IllegalStateException("Failed to add to favorite")
        whenever(favoriteShowsDao.addShow(ModelUtil.showEntity)).thenThrow(exception)
        runCatching {
            favoriteLocalDataSource.addToFavorite(ModelUtil.showEntity)
        }.onFailure { e ->
            verify(favoriteShowsDao, Mockito.times(1))
                .addShow(ModelUtil.showEntity)
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `remove from favorites success`() = runTest {
        whenever(favoriteShowsDao.deleteShow(ModelUtil.showId)).thenReturn(Unit)
        val result = favoriteLocalDataSource.removeFromFavorite(ModelUtil.showId)
        verify(favoriteShowsDao, Mockito.times(1))
            .deleteShow(ModelUtil.showId)
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun `remove from favorites failed`() = runTest {
        val exception = IllegalStateException("Failed to remove from favorite")
        whenever(favoriteShowsDao.deleteShow(ModelUtil.showId)).thenThrow(exception)
        runCatching {
            favoriteLocalDataSource.removeFromFavorite(ModelUtil.showId)
        }.onFailure { e ->
            verify(favoriteShowsDao, Mockito.times(1))
                .deleteShow(ModelUtil.showId)
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `update favorite success`() = runTest {
        whenever(favoriteShowsDao.updateShow(ModelUtil.showEntity)).thenReturn(Unit)
        val result = favoriteLocalDataSource.updateFavorite(ModelUtil.showEntity)
        verify(favoriteShowsDao, Mockito.times(1))
            .updateShow(ModelUtil.showEntity)
        assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun `update favorite failed`() = runTest {
        val exception = IllegalStateException("Failed to update favorite")
        whenever(favoriteShowsDao.updateShow(ModelUtil.showEntity)).thenThrow(exception)
        runCatching {
            favoriteLocalDataSource.updateFavorite(ModelUtil.showEntity)
        }.onFailure { e ->
            verify(favoriteShowsDao, Mockito.times(1))
                .updateShow(ModelUtil.showEntity)
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `get favorite success`() = runTest {
        whenever(favoriteShowsDao.getShow(ModelUtil.showId)).thenReturn(ModelUtil.showEntity)
        val result = favoriteLocalDataSource.getShow(ModelUtil.showId)
        verify(favoriteShowsDao, Mockito.times(1))
            .getShow(ModelUtil.showId)
        assertThat(result).isEqualTo(ModelUtil.showEntity)
    }

    @Test
    fun `get favorite failed`() = runTest {
        val exception = IllegalStateException("Failed to get favorite")
        whenever(favoriteShowsDao.getShow(ModelUtil.showId)).thenThrow(exception)
        runCatching {
            favoriteLocalDataSource.getShow(ModelUtil.showId)
        }.onFailure { e ->
            verify(favoriteShowsDao, Mockito.times(1))
                .getShow(ModelUtil.showId)
            assertThat(e).isEqualTo(exception)
        }
    }
}
