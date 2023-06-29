package com.example.tvschedule.presentation.show_search.model

import com.example.tvschedule.presentation.common.ViewState


data class SearchUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false
): ViewState
