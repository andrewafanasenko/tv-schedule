package com.example.tvschedule.presentation.search

import com.example.tvschedule.MainDispatcherRule
import com.example.tvschedule.data.util.ModelUtil
import com.example.tvschedule.domain.favorite.use_case.AddToFavoritesUseCase
import com.example.tvschedule.domain.favorite.use_case.GetFavoritesUseCase
import com.example.tvschedule.domain.favorite.use_case.RemoveFromFavoritesUseCase
import com.example.tvschedule.domain.search.use_case.SearchShowUseCase
import com.example.tvschedule.domain.show_details.model.Show
import com.example.tvschedule.presentation.search.model.SearchUiEvent
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private val searchShowUseCase = mock<SearchShowUseCase>()
    private val getFavoritesUseCase = mock<GetFavoritesUseCase>()
    private val addToFavoritesUseCase = mock<AddToFavoritesUseCase>()
    private val removeFromFavoritesUseCase = mock<RemoveFromFavoritesUseCase>()

    private lateinit var viewModel : SearchViewModel

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    @Test
    fun `get favorite shows success`() = runTest {
        val flow = flow { emit(listOf(ModelUtil.show)) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        viewModel = SearchViewModel(
            searchShowUseCase = searchShowUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        verify(getFavoritesUseCase, times(1)).invoke()
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
        assertThat(state.searchQuery).isEmpty()
    }

    @Test
    fun `get favorite shows failed`() = runTest {
        whenever(getFavoritesUseCase.invoke())
            .thenReturn(Result.failure(IllegalStateException("Failed to get favorites")))
        viewModel = SearchViewModel(
            searchShowUseCase = searchShowUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
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
    fun `search shows success`() = runTest {
        val flow = flow { emit(ModelUtil.shows) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        whenever(searchShowUseCase.invoke(ModelUtil.searchQuery))
            .thenReturn(Result.success(ModelUtil.shows))
        viewModel = SearchViewModel(
            searchShowUseCase = searchShowUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        viewModel.setEvent(SearchUiEvent.OnQueryChange(ModelUtil.searchQuery))
        advanceUntilIdle()
        verify(getFavoritesUseCase, times(1)).invoke()
        verify(searchShowUseCase, times(1)).invoke(ModelUtil.searchQuery)
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
        assertThat(state.searchQuery).isNotEmpty()
        assertThat(state.shows).isNotEmpty()
    }

    @Test
    fun `search shows failed`() = runTest {
        val flow = flow { emit(ModelUtil.shows) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        whenever(searchShowUseCase.invoke(ModelUtil.searchQuery))
            .thenReturn(Result.failure(IllegalStateException("Failed to search shows")))
        viewModel = SearchViewModel(
            searchShowUseCase = searchShowUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        viewModel.setEvent(SearchUiEvent.OnQueryChange(ModelUtil.searchQuery))
        advanceUntilIdle()
        verify(getFavoritesUseCase, times(1)).invoke()
        verify(searchShowUseCase, times(1)).invoke(ModelUtil.searchQuery)
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isTrue()
        assertThat(state.searchQuery).isNotEmpty()
        assertThat(state.shows).isEmpty()
    }

    @Test
    fun `remove from favorite shows success`() = runTest {
        val flow = flow { emit(listOf(ModelUtil.show)) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        whenever(removeFromFavoritesUseCase.invoke(ModelUtil.showId))
            .thenReturn(Result.success(Unit))
        viewModel = SearchViewModel(
            searchShowUseCase = searchShowUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        viewModel.setEvent(SearchUiEvent.OnFavoriteClick(ModelUtil.showId, false))
        verify(removeFromFavoritesUseCase, times(1)).invoke(ModelUtil.showId)
        verify(getFavoritesUseCase, times(1)).invoke()
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
        assertThat(state.searchQuery).isEmpty()
    }

    @Test
    fun `add to favorite shows success`() = runTest {
        val flow = flow { emit(emptyList<Show>()) }
        whenever(searchShowUseCase.invoke(any())).thenReturn(Result.success(ModelUtil.shows))
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        whenever(addToFavoritesUseCase.invoke(ModelUtil.show)).thenReturn(Result.success(Unit))
        viewModel = SearchViewModel(
            searchShowUseCase = searchShowUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        viewModel.setEvent(SearchUiEvent.Retry)
        advanceUntilIdle()
        viewModel.setEvent(SearchUiEvent.OnFavoriteClick(ModelUtil.showId, true))
        verify(searchShowUseCase, times(1)).invoke(any())
        verify(getFavoritesUseCase, times(1)).invoke()
        verify(addToFavoritesUseCase, times(1)).invoke(ModelUtil.show)
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
        assertThat(state.searchQuery).isEmpty()
    }
}
