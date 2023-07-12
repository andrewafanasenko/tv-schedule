package com.example.tvschedule.domain.favorite.use_case

import com.example.tvschedule.data.util.ModelUtil.show
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class GetFavoritesUseCaseTest {

    private val favoriteRepository = Mockito.mock<FavoriteRepository>()

    private val getFavoritesUseCase = GetFavoritesUseCase(
        favoriteRepository = favoriteRepository,
        ioDispatcher = Dispatchers.IO
    )

    @Test
    fun `get favorite success`() = runTest {
        val shows = listOf(show)
        whenever(favoriteRepository.favoriteShows()).thenReturn(flowOf(shows))
        val result = getFavoritesUseCase.invoke()
        assertThat(result.isSuccess).isEqualTo(true)
        result.getOrNull()?.collect { res ->
            verify(favoriteRepository, times(1)).favoriteShows()
            assertThat(res).isEqualTo(shows)
        }
    }

    @Test
    fun `get favorite failed`() = runTest {
        val exception = IllegalStateException("Failed to get favorite shows")
        whenever(favoriteRepository.favoriteShows()).thenThrow(exception)
        val result = getFavoritesUseCase.invoke()
        assertThat(result.isFailure).isEqualTo(true)
        verify(favoriteRepository, times(1)).favoriteShows()
    }
}
