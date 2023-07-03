package com.example.tvschedule.presentation.search

import androidx.lifecycle.viewModelScope
import com.example.tvschedule.domain.favorite.use_case.AddToFavoritesUseCase
import com.example.tvschedule.domain.favorite.use_case.GetFavoritesUseCase
import com.example.tvschedule.domain.favorite.use_case.RemoveFromFavoritesUseCase
import com.example.tvschedule.domain.search.use_case.SearchShowUseCase
import com.example.tvschedule.presentation.common.BaseViewModel
import com.example.tvschedule.presentation.search.model.SearchData
import com.example.tvschedule.presentation.search.model.SearchUiEvent
import com.example.tvschedule.presentation.search.model.SearchUiState
import com.example.tvschedule.presentation.search.model.ShowItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchShowUseCase: SearchShowUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : BaseViewModel<SearchUiEvent, SearchUiState>() {

    private var searchShowJob: Job? = null

    private val searchData = MutableStateFlow(SearchData())

    init {
        listenToFavoriteShow()
    }

    override val viewState: StateFlow<SearchUiState> = searchData.map { data ->
        SearchUiState(
            isLoading = data.isLoading,
            isError = data.isError,
            searchQuery = data.searchQuery,
            shows = data.shows.map { show ->
                ShowItem(
                    id = show.id,
                    name = show.showName,
                    summary = show.summary,
                    coverUrl = show.coverUrl,
                    rating = show.rating,
                    genres = show.genres,
                    isFavourite = data.favoriteShowsIds.any { show.id == it }
                )
            }
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, SearchUiState())

    private fun listenToFavoriteShow() {
        viewModelScope.launch {
            getFavoritesUseCase.invoke()
                .onSuccess { favoritesFlow ->
                    favoritesFlow.collect { shows ->
                        searchData.update {
                            it.copy(favoriteShowsIds = shows.map { show -> show.id })
                        }
                    }
                }
                .onFailure {
                    searchData.update { it.copy(isLoading = false, isError = true) }
                }
        }
    }

    override fun handleEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnQueryChange -> {
                searchData.update { it.copy(searchQuery = event.query) }
                searchShow()
            }

            SearchUiEvent.Retry -> {
                searchShow()
            }

            is SearchUiEvent.OnFavoriteClick -> {
                if (event.isFavorite) {
                    addToFavorite(event.showId)
                } else {
                    removeFromFavorite(event.showId)
                }
            }
        }
    }

    private fun searchShow() {
        searchShowJob?.cancel()
        searchData.update { it.copy(isLoading = true) }
        searchShowJob = viewModelScope.launch {
            delay(TYPING_DELAY)
            searchShowUseCase.invoke(searchData.value.searchQuery)
                .onSuccess { result ->
                    searchData.update {
                        it.copy(
                            isLoading = false,
                            isError = false,
                            shows = result
                        )
                    }
                }
                .onFailure {
                    searchData.update { it.copy(isLoading = false, isError = true) }
                }
        }
    }

    private fun addToFavorite(showId: Long) {
        viewModelScope.launch {
            searchData.value.shows.firstOrNull { it.id == showId }?.let {
                addToFavoritesUseCase.invoke(it)
            }
        }
    }

    private fun removeFromFavorite(showId: Long) {
        viewModelScope.launch {
            removeFromFavoritesUseCase.invoke(showId)
        }
    }

    companion object {

        private const val TYPING_DELAY = 500L
    }
}
