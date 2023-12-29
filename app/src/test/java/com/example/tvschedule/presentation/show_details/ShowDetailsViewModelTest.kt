package com.example.tvschedule.presentation.show_details

import androidx.lifecycle.SavedStateHandle
import com.example.tvschedule.MainDispatcherRule
import com.example.tvschedule.data.util.ModelUtil
import com.example.tvschedule.domain.favorite.use_case.AddToFavoritesUseCase
import com.example.tvschedule.domain.favorite.use_case.GetFavoritesUseCase
import com.example.tvschedule.domain.favorite.use_case.RemoveFromFavoritesUseCase
import com.example.tvschedule.domain.show_details.model.Show
import com.example.tvschedule.domain.show_details.use_case.GetShowDetailsUseCase
import com.example.tvschedule.presentation.model.Screen
import com.example.tvschedule.presentation.show_details.model.ShowDetailsUiEvent
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class ShowDetailsViewModelTest {

    private val savedStateHandle = mock<SavedStateHandle>()
    private val getShowDetailsUseCase = mock<GetShowDetailsUseCase>()
    private val getFavoritesUseCase = mock<GetFavoritesUseCase>()
    private val addToFavoritesUseCase = mock<AddToFavoritesUseCase>()
    private val removeFromFavoritesUseCase = mock<RemoveFromFavoritesUseCase>()

    private lateinit var viewModel: ShowDetailsViewModel

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    @Test
    fun `get favorite show success`() = runTest {
        val flow = flow { emit(emptyList<Show>()) }
        val detailsFlow = flow { emit(ModelUtil.showDetails) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        whenever(getShowDetailsUseCase.invoke(ModelUtil.showId)).thenReturn(detailsFlow)
        whenever(savedStateHandle.get(Screen.SHOW_ID) as? Long).thenReturn(ModelUtil.showId)
        viewModel = ShowDetailsViewModel(
            savedStateHandle = savedStateHandle,
            getShowDetailsUseCase = getShowDetailsUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        verify(savedStateHandle, times(1)).get(Screen.SHOW_ID) as? Long
        verify(getFavoritesUseCase, times(1)).invoke()
        verify(getShowDetailsUseCase, times(1)).invoke(ModelUtil.showId)
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
        assertThat(state.isFavorite).isTrue()
    }

    @Test
    fun `get favorite show failed`() = runTest {
        val flow = flow { emit(ModelUtil.shows) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        whenever(getShowDetailsUseCase.invoke(ModelUtil.showId))
            .thenReturn(flow { throw RuntimeException("Failed to get favorite show") })
        whenever(savedStateHandle.get(Screen.SHOW_ID) as? Long).thenReturn(ModelUtil.showId)
        viewModel = ShowDetailsViewModel(
            savedStateHandle = savedStateHandle,
            getShowDetailsUseCase = getShowDetailsUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        verify(savedStateHandle, times(1)).get(Screen.SHOW_ID) as? Long
        verify(getFavoritesUseCase, times(1)).invoke()
        verify(getShowDetailsUseCase, times(1)).invoke(ModelUtil.showId)
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isTrue()
        assertThat(state.isFavorite).isTrue()
    }

    @Test
    fun `add to favorites success`() = runTest {
        val flow = flow { emit(ModelUtil.shows) }
        val detailsFlow = flow { emit(ModelUtil.showDetails.copy(isFavorite = false)) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        whenever(getShowDetailsUseCase.invoke(ModelUtil.showId)).thenReturn(detailsFlow)
        whenever(addToFavoritesUseCase.invoke(ModelUtil.show)).thenReturn(Result.success(Unit))
        whenever(savedStateHandle.get(Screen.SHOW_ID) as? Long).thenReturn(ModelUtil.showId)
        viewModel = ShowDetailsViewModel(
            savedStateHandle = savedStateHandle,
            getShowDetailsUseCase = getShowDetailsUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        viewModel.setEvent(ShowDetailsUiEvent.OnFavoriteClick)
        verify(savedStateHandle, times(1)).get(Screen.SHOW_ID) as? Long
        verify(getFavoritesUseCase, times(1)).invoke()
        verify(getShowDetailsUseCase, times(1)).invoke(ModelUtil.showId)
        verify(addToFavoritesUseCase, times(1)).invoke(ModelUtil.show)
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
    }

    @Test
    fun `remove from favorites success`() = runTest {
        val flow = flow { emit(ModelUtil.shows) }
        val detailsFlow = flow { emit(ModelUtil.showDetails.copy()) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        whenever(getShowDetailsUseCase.invoke(ModelUtil.showId)).thenReturn(detailsFlow)
        whenever(removeFromFavoritesUseCase.invoke(ModelUtil.showId))
            .thenReturn(Result.success(Unit))
        whenever(savedStateHandle.get(Screen.SHOW_ID) as? Long).thenReturn(ModelUtil.showId)
        viewModel = ShowDetailsViewModel(
            savedStateHandle = savedStateHandle,
            getShowDetailsUseCase = getShowDetailsUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        viewModel.setEvent(ShowDetailsUiEvent.OnFavoriteClick)
        verify(savedStateHandle, times(1)).get(Screen.SHOW_ID) as? Long
        verify(getFavoritesUseCase, times(1)).invoke()
        verify(getShowDetailsUseCase, times(1)).invoke(ModelUtil.showId)
        verify(removeFromFavoritesUseCase, times(1)).invoke(ModelUtil.showId)
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
    }

    @Test
    fun `show all casts success`() = runTest {
        val flow = flow { emit(ModelUtil.shows) }
        val detailsFlow = flow { emit(ModelUtil.showDetails) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        whenever(getShowDetailsUseCase.invoke(ModelUtil.showId)).thenReturn(detailsFlow)
        whenever(savedStateHandle.get(Screen.SHOW_ID) as? Long).thenReturn(ModelUtil.showId)
        viewModel = ShowDetailsViewModel(
            savedStateHandle = savedStateHandle,
            getShowDetailsUseCase = getShowDetailsUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        viewModel.setEvent(ShowDetailsUiEvent.OnShowAllCastClick)
        verify(savedStateHandle, times(1)).get(Screen.SHOW_ID) as? Long
        verify(getFavoritesUseCase, times(1)).invoke()
        verify(getShowDetailsUseCase, times(1)).invoke(ModelUtil.showId)
        val state = viewModel.viewState.value
        assertThat(state.isViewAllCastButtonVisible).isFalse()
    }

    @Test
    fun `show all seasons success`() = runTest {
        val flow = flow { emit(ModelUtil.shows) }
        val detailsFlow = flow { emit(ModelUtil.showDetails) }
        whenever(getFavoritesUseCase.invoke()).thenReturn(Result.success(flow))
        whenever(getShowDetailsUseCase.invoke(ModelUtil.showId)).thenReturn(detailsFlow)
        whenever(savedStateHandle.get(Screen.SHOW_ID) as? Long).thenReturn(ModelUtil.showId)
        viewModel = ShowDetailsViewModel(
            savedStateHandle = savedStateHandle,
            getShowDetailsUseCase = getShowDetailsUseCase,
            getFavoritesUseCase = getFavoritesUseCase,
            addToFavoritesUseCase = addToFavoritesUseCase,
            removeFromFavoritesUseCase = removeFromFavoritesUseCase
        )
        viewModel.setEvent(ShowDetailsUiEvent.OnShowAllSeasonsClick)
        verify(savedStateHandle, times(1)).get(Screen.SHOW_ID) as? Long
        verify(getFavoritesUseCase, times(1)).invoke()
        verify(getShowDetailsUseCase, times(1)).invoke(ModelUtil.showId)
        val state = viewModel.viewState.value
        assertThat(state.isViewAllSeasonsButtonVisible).isFalse()
    }
}
