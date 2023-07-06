package com.example.tvschedule.presentation.show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.tvschedule.domain.favorite.use_case.GetFavoritesUseCase
import com.example.tvschedule.domain.show_details.use_case.GetShowDetailsUseCase
import com.example.tvschedule.presentation.common.BaseViewModel
import com.example.tvschedule.presentation.common.getPersonLifeRange
import com.example.tvschedule.presentation.model.Screen
import com.example.tvschedule.presentation.show_details.model.CastItem
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
) : BaseViewModel<ShowDetailsUiEvent, ShowDetailsUiState>() {

    private val showId = checkNotNull(savedStateHandle[Screen.SHOW_ID]) as Long

    private val showDetailsData = MutableStateFlow(ShowDetailsData())

    override val viewState: StateFlow<ShowDetailsUiState> = showDetailsData.map { data ->
        ShowDetailsUiState(
            isLoading = data.isLoading,
            isError = data.isError,
            coverUrl = data.show?.originalCoverUrl?.ifBlank { data.show.coverUrl }.orEmpty(),
            showName = data.show?.showName.orEmpty(),
            summary = data.show?.summary.orEmpty(),
            isFavorite = data.isFavorite,
            cast = data.cast.take(MAX_ITEMS_COUNT).map { person ->
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
            isViewAllCastButtonVisible = data.cast.size > MAX_ITEMS_COUNT
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ShowDetailsUiState())

    init {
        listenToFavoriteShows()
        loadShowDetails()
    }

    override fun handleEvent(event: ShowDetailsUiEvent) {

    }

    private fun listenToFavoriteShows() {
        viewModelScope.launch {
            getFavoritesUseCase.invoke()
                .onSuccess { favoritesFlow ->
                    favoritesFlow.collect { shows ->
                        shows.firstOrNull { it.id == showId }?.let { show ->
                            showDetailsData.update {
                                it.copy(show = show, isFavorite = true)
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
                            cast = showDetails.show.cast
                        )
                    }
                }
        }
    }

    companion object {

        private const val MAX_ITEMS_COUNT = 4
    }
}
