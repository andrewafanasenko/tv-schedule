package com.example.tvschedule.presentation.search.model

import com.example.tvschedule.presentation.common.ViewState


data class SearchUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchQuery: String = "",
    val shows: List<ShowItem> = emptyList()
): ViewState
