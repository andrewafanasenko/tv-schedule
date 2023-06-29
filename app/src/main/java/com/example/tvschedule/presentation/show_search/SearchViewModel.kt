package com.example.tvschedule.presentation.show_search

import androidx.lifecycle.viewModelScope
import com.example.tvschedule.presentation.common.BaseViewModel
import com.example.tvschedule.presentation.show_search.model.SearchData
import com.example.tvschedule.presentation.show_search.model.SearchUiEvent
import com.example.tvschedule.presentation.show_search.model.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(

): BaseViewModel<SearchUiEvent, SearchUiState>() {

    private val searchData = MutableStateFlow(SearchData())

    override val viewState: StateFlow<SearchUiState> = searchData.map { data ->
        SearchUiState(
            isLoading = data.isLoading,
            isError = data.isError,
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, SearchUiState())

    override fun handleEvent(event: SearchUiEvent) {
        when(event) {

        }
    }
}
