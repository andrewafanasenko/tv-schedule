package com.example.tvschedule.presentation.favorite

import com.example.tvschedule.MainDispatcherRule
import com.example.tvschedule.data.util.ModelUtil
import com.example.tvschedule.domain.favorite.use_case.GetFavoritesUseCase
import com.example.tvschedule.domain.favorite.use_case.RemoveFromFavoritesUseCase
import com.example.tvschedule.presentation.favorite.model.FavoriteUiEvent
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class FavoriteViewModelTest {

    private val getFavoritesUseCase = mock<GetFavoritesUseCase>()

    private val removeFromFavoritesUseCase = mock<RemoveFromFavoritesUseCase>()

    private lateinit var viewModel : FavoriteViewModel

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    @Test
    fun `get favorite shows success`() = runTest {
        val flow = flow { emit(listOf(ModelUtil.show)) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        viewModel = FavoriteViewModel(
            getFavoritesUseCase = getFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        verify(getFavoritesUseCase, times(1)).invoke()
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
        assertThat(state.searchQuery).isEmpty()
        assertThat(state.shows).isNotEmpty()
    }

    @Test
    fun `get favorite shows failed`() = runTest {
        whenever(getFavoritesUseCase.invoke())
            .thenReturn(Result.failure(IllegalStateException("Failed to get favorites")))
        viewModel = FavoriteViewModel(
            getFavoritesUseCase = getFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        verify(getFavoritesUseCase, times(1)).invoke()
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isTrue()
        assertThat(state.searchQuery).isEmpty()
        assertThat(state.shows).isEmpty()
    }

    @Test
    fun `remove from favorite shows success`() = runTest {
        val flow = flow { emit(listOf(ModelUtil.show)) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        whenever(removeFromFavoritesUseCase.invoke(ModelUtil.showId))
            .thenReturn(Result.success(Unit))
        viewModel = FavoriteViewModel(
            getFavoritesUseCase = getFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        viewModel.setEvent(FavoriteUiEvent.OnFavoriteClick(ModelUtil.showId))
        verify(removeFromFavoritesUseCase, times(1)).invoke(ModelUtil.showId)
        verify(getFavoritesUseCase, times(1)).invoke()
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
        assertThat(state.searchQuery).isEmpty()
        assertThat(state.shows).isNotEmpty()
    }

    @Test
    fun `handle retry event success`() = runTest {
        val flow = flow { emit(listOf(ModelUtil.show)) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        viewModel = FavoriteViewModel(
            getFavoritesUseCase = getFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        whenever(viewModel.setEvent(FavoriteUiEvent.Retry)).thenReturn(Unit)
        verify(getFavoritesUseCase, times(1)).invoke()
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
        assertThat(state.searchQuery).isEmpty()
        assertThat(state.shows).isNotEmpty()
    }

    @Test
    fun `handle query change event success`() = runTest {
        val flow = flow { emit(listOf(ModelUtil.show)) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        viewModel = FavoriteViewModel(
            getFavoritesUseCase = getFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        whenever(viewModel.setEvent(FavoriteUiEvent.OnQueryChange(ModelUtil.searchQuery)))
            .thenReturn(Unit)
        verify(getFavoritesUseCase, times(0)).invoke()
        val state = viewModel.viewState.value
        assertThat(state.isError).isFalse()
        assertThat(state.searchQuery).isNotEmpty()
    }
}
