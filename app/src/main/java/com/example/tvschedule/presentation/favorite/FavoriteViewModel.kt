package com.example.tvschedule.presentation.favorite

import androidx.lifecycle.viewModelScope
import com.example.tvschedule.domain.favorite.use_case.GetFavoritesUseCase
import com.example.tvschedule.domain.favorite.use_case.RemoveFromFavoritesUseCase
import com.example.tvschedule.presentation.common.BaseViewModel
import com.example.tvschedule.presentation.favorite.model.FavoriteData
import com.example.tvschedule.presentation.favorite.model.FavoriteUiEvent
import com.example.tvschedule.presentation.favorite.model.FavoriteUiState
import com.example.tvschedule.presentation.search.model.ShowItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : BaseViewModel<FavoriteUiEvent, FavoriteUiState>() {

    private val favoriteData = MutableStateFlow(FavoriteData())

    override val viewState: StateFlow<FavoriteUiState> = favoriteData.map { data ->
        FavoriteUiState(
            isLoading = data.isLoading,
            isError = data.isError,
            searchQuery = data.searchQuery,
            shows = data.shows.filter {
                it.showName.contains(data.searchQuery, ignoreCase = true)
            }.map { show ->
                ShowItem(
                    id = show.id,
                    name = show.showName,
                    summary = show.summary,
                    coverUrl = show.coverUrl,
                    rating = show.rating,
                    genres = show.genres,
                    isFavourite = true
                )
            }
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, FavoriteUiState())

    init {
        getFavoriteShows()
    }

    private fun getFavoriteShows() {
        viewModelScope.launch {
            favoriteData.update { it.copy(isLoading = true) }
            getFavoritesUseCase.invoke()
                .onSuccess { favoritesFlow ->
                    favoritesFlow.collect { shows ->
                        favoriteData.update {
                            it.copy(
                                shows = shows,
                                isLoading = false,
                                isError = false
                            )
                        }
                    }
                }
                .onFailure {
                    favoriteData.update { it.copy(isLoading = false, isError = true) }
                }
        }
    }

    override fun handleEvent(event: FavoriteUiEvent) {
        when (event) {
            FavoriteUiEvent.Retry -> {
                getFavoriteShows()
            }

            is FavoriteUiEvent.OnFavoriteClick -> {
                removeFromFavorite(event.showId)
            }

            is FavoriteUiEvent.OnQueryChange -> {
                favoriteData.update { it.copy(searchQuery = event.query) }
            }
        }
    }

    private fun removeFromFavorite(showId: Long) {
        viewModelScope.launch {
            removeFromFavoritesUseCase.invoke(showId)
        }
    }
}
