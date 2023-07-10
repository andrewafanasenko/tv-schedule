package com.example.tvschedule.presentation.show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.tvschedule.domain.favorite.use_case.AddToFavoritesUseCase
import com.example.tvschedule.domain.favorite.use_case.GetFavoritesUseCase
import com.example.tvschedule.domain.favorite.use_case.RemoveFromFavoritesUseCase
import com.example.tvschedule.domain.show_details.use_case.GetShowDetailsUseCase
import com.example.tvschedule.presentation.common.BaseViewModel
import com.example.tvschedule.presentation.common.getPersonLifeRange
import com.example.tvschedule.presentation.common.getSeasonDateRange
import com.example.tvschedule.presentation.model.Screen
import com.example.tvschedule.presentation.show_details.model.CastItem
import com.example.tvschedule.presentation.show_details.model.SeasonItem
import com.example.tvschedule.presentation.show_details.model.ShowDetailsData
import com.example.tvschedule.presentation.show_details.model.ShowDetailsUiEvent
import com.example.tvschedule.presentation.show_details.model.ShowDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShowDetailsUseCase: GetShowDetailsUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : BaseViewModel<ShowDetailsUiEvent, ShowDetailsUiState>() {

    private val showId = checkNotNull(savedStateHandle[Screen.SHOW_ID]) as Long

    private val showDetailsData = MutableStateFlow(ShowDetailsData())

    override val viewState: StateFlow<ShowDetailsUiState> = showDetailsData.map { data ->
        ShowDetailsUiState(
            isLoading = data.isLoading,
            isError = data.isError,
            coverUrl = data.show?.originalCoverUrl?.ifBlank { data.show.coverUrl }.orEmpty(),
            rating = data.show?.rating,
            showName = data.show?.showName.orEmpty(),
            genres = data.show?.genres.orEmpty(),
            summary = data.show?.summary.orEmpty(),
            isFavorite = data.isFavorite,
            cast = data.cast.take(data.limitCast).map { person ->
                CastItem(
                    id = person.id,
                    fullName = person.fullName,
                    characterName = if (person.self.not()) person.characterName else "",
                    lifeDateRange = person.birthday.getPersonLifeRange(
                        birthPlace = person.birthPlace,
                        deathday = person.deathday
                    ),
                    imageUrl = person.imageUrl
                )
            },
            isViewAllCastButtonVisible = data.isViewAllCastButtonVisible,
            seasons = data.seasons.take(data.limitSeasons).map { season ->
                SeasonItem(
                    id = season.id,
                    name = season.name.ifBlank { season.number.toString() },
                    summary = season.summary,
                    episodesCount = season.episodeOrder,
                    dateRange = season.premiereDate.getSeasonDateRange(season.endDate),
                    imageUrl = season.imageUrl
                )
            },
            isViewAllSeasonsButtonVisible = data.isViewAllSeasonsButtonVisible
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ShowDetailsUiState())

    init {
        listenToFavoriteShows()
        loadShowDetails()
    }

    override fun handleEvent(event: ShowDetailsUiEvent) {
        when (event) {
            ShowDetailsUiEvent.OnFavoriteClick -> {
                addRemoveFromFavorite()
            }

            ShowDetailsUiEvent.OnShowAllCastClick -> {
                showAllCast()
            }

            ShowDetailsUiEvent.OnShowAllSeasonsClick -> {
                showAllSeasons()
            }
        }
    }

    private fun addRemoveFromFavorite() {
        val show = showDetailsData.value.show ?: return
        viewModelScope.launch {
            if (showDetailsData.value.isFavorite) {
                removeFromFavoritesUseCase.invoke(showId)
            } else {
                addToFavoritesUseCase.invoke(show)
            }
        }
    }

    private fun showAllCast() {
        showDetailsData.update { it.copy(isAllCastVisible = true) }
    }

    private fun showAllSeasons() {
        showDetailsData.update { it.copy(isAllSeasonsVisible = true) }
    }

    private fun listenToFavoriteShows() {
        viewModelScope.launch {
            getFavoritesUseCase.invoke()
                .onSuccess { favoritesFlow ->
                    favoritesFlow.collect { shows ->
                        showDetailsData.update { data ->
                            shows.firstOrNull { it.id == showId }?.let { show ->
                                data.copy(show = show, isFavorite = true)
                            } ?: run {
                                data.copy(isFavorite = false)
                            }
                        }
                    }
                }
        }
    }

    private fun loadShowDetails() {
        viewModelScope.launch {
            showDetailsData.update { it.copy(isLoading = true) }
            getShowDetailsUseCase.invoke(showId)
                .catch {
                    showDetailsData.update { it.copy(isLoading = false, isError = true) }
                }
                .collect { showDetails ->
                    showDetailsData.update {
                        it.copy(
                            isLoading = false,
                            isError = false,
                            show = showDetails.show,
                            isFavorite = showDetails.isFavorite,
                            cast = showDetails.show.cast,
                            seasons = showDetails.show.seasons
                        )
                    }
                }
        }
    }
}
