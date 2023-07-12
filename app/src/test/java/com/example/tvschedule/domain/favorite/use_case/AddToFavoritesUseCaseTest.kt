package com.example.tvschedule.domain.favorite.use_case

import com.example.tvschedule.data.util.ModelUtil.show
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class AddToFavoritesUseCaseTest {

    private val favoriteRepository = mock<FavoriteRepository>()

    private val addToFavoritesUseCase = AddToFavoritesUseCase(
        favoriteRepository = favoriteRepository,
        ioDispatcher = Dispatchers.IO
    )

    @Test
    fun `add to favorite success`() = runTest {
        whenever(favoriteRepository.addToFavorite(show)).thenReturn(Unit)
        val result = addToFavoritesUseCase.invoke(show)
        assertThat(result).isEqualTo(Result.success(Unit))
        verify(favoriteRepository, times(1)).addToFavorite(show)
    }

    @Test
    fun `add to favorite failed`() = runTest {
        val exception = IllegalStateException("Failed to add to favorite")
        whenever(favoriteRepository.addToFavorite(show)).thenThrow(exception)
        val result = addToFavoritesUseCase.invoke(show)
        assertThat(result).isEqualTo(Result.failure<IllegalStateException>(exception))
        verify(favoriteRepository, times(1)).addToFavorite(show)
    }
}
