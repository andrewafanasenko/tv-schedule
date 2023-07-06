package com.example.tvschedule.presentation.show_details.model

import com.example.tvschedule.presentation.common.ViewState


data class ShowDetailsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val coverUrl: String = "",
    val showName: String = "",
    val summary: String = "",
    val isFavorite: Boolean = false,
    val cast: List<CastItem> = emptyList(),
    val isViewAllCastButtonVisible: Boolean = false,
    val seasons: List<SeasonItem> = emptyList(),
    val isViewAllSeasonsButtonVisible: Boolean = false,
) : ViewState
