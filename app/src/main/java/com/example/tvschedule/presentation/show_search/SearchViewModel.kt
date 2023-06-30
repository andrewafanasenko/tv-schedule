package com.example.tvschedule.presentation.show_search

import androidx.lifecycle.viewModelScope
import com.example.tvschedule.domain.search.use_case.SearchShowUseCase
import com.example.tvschedule.presentation.common.BaseViewModel
import com.example.tvschedule.presentation.show_search.model.SearchData
import com.example.tvschedule.presentation.show_search.model.SearchUiEvent
import com.example.tvschedule.presentation.show_search.model.SearchUiState
import com.example.tvschedule.presentation.show_search.model.ShowItem
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
    private val searchShowUseCase: SearchShowUseCase
) : BaseViewModel<SearchUiEvent, SearchUiState>() {

    private var searchShowJob: Job? = null

    private val searchData = MutableStateFlow(SearchData())

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
                    rating = show.rating
                )
            }
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, SearchUiState())

    override fun handleEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnQueryChange -> {
                searchData.update { it.copy(searchQuery = event.query) }
                searchShow()
            }
            SearchUiEvent.Retry -> {
                searchShow()
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

    companion object {

        private const val TYPING_DELAY = 500L
    }
}
