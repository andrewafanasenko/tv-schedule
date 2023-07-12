package com.example.tvschedule.domain.favorite.use_case

import com.example.tvschedule.data.util.ModelUtil.show
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class RemoveFromFavoritesUseCaseTest {

    private val favoriteRepository = Mockito.mock<FavoriteRepository>()

    private val removeFromFavoritesUseCase = RemoveFromFavoritesUseCase(
        favoriteRepository = favoriteRepository,
        ioDispatcher = Dispatchers.IO
    )

    @Test
    fun `remove from favorite success`() = runTest {
        whenever(favoriteRepository.removeFromFavorite(show.id)).thenReturn(Unit)
        val result = removeFromFavoritesUseCase.invoke(show.id)
        assertThat(result).isEqualTo(Result.success(Unit))
        verify(favoriteRepository, times(1)).removeFromFavorite(show.id)
    }

    @Test
    fun `remove from favorite failed`() = runTest {
        val exception = IllegalStateException("Failed to remove from favorites")
        whenever(favoriteRepository.removeFromFavorite(show.id)).thenThrow(exception)
        val result = removeFromFavoritesUseCase.invoke(show.id)
        assertThat(result).isEqualTo(Result.failure<IllegalStateException>(exception))
        verify(favoriteRepository, times(1)).removeFromFavorite(show.id)
    }
}
