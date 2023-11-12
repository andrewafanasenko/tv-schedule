package com.example.tvschedule.presentation.search.model

import com.example.tvschedule.presentation.common.ViewState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class SearchUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchQuery: String = "",
    val shows: ImmutableList<ShowItem> = persistentListOf()
): ViewState
