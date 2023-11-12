package com.example.tvschedule.presentation.favorite.model

import com.example.tvschedule.presentation.common.ViewState
import com.example.tvschedule.presentation.search.model.ShowItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class FavoriteUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchQuery: String = "",
    val shows: ImmutableList<ShowItem> = persistentListOf()
) : ViewState
