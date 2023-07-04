package com.example.tvschedule.presentation.show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.tvschedule.domain.show_details.use_case.GetShowDetailsUseCase
import com.example.tvschedule.presentation.common.BaseViewModel
import com.example.tvschedule.presentation.model.Screen
import com.example.tvschedule.presentation.show_details.model.ShowDetailsData
import com.example.tvschedule.presentation.show_details.model.ShowDetailsUiEvent
import com.example.tvschedule.presentation.show_details.model.ShowDetailsUiState
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
class ShowDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShowDetailsUseCase: GetShowDetailsUseCase
) : BaseViewModel<ShowDetailsUiEvent, ShowDetailsUiState>() {

    private val showId = checkNotNull(savedStateHandle[Screen.SHOW_ID]) as Long

    private val showDetailsData = MutableStateFlow(ShowDetailsData())

    override val viewState: StateFlow<ShowDetailsUiState> = showDetailsData.map { data ->
        ShowDetailsUiState(
            isLoading = data.isLoading,
            isError = data.isError,
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ShowDetailsUiState())

    init {
        loadShowDetails()
    }

    override fun handleEvent(event: ShowDetailsUiEvent) {

    }

    private fun loadShowDetails() {
        viewModelScope.launch {
            showDetailsData.update { it.copy(isLoading = true) }
            getShowDetailsUseCase.invoke(showId)
                .onSuccess { show ->
                    showDetailsData.update {
                        it.copy(isLoading = false, isError = false, show = show)
                    }
                }
                .onFailure {
                    showDetailsData.update { it.copy(isLoading = false, isError = true) }
                }
        }
    }
}
