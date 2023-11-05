package com.example.tvschedule.presentation.show_details.model

import com.example.tvschedule.presentation.common.ViewState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class ShowDetailsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val coverUrl: String = "",
    val rating: Double? = null,
    val showName: String = "",
    val genres: ImmutableList<String> = persistentListOf(),
    val summary: String = "",
    val isFavorite: Boolean = false,
    val cast: ImmutableList<CastItem> = persistentListOf(),
    val isViewAllCastButtonVisible: Boolean = false,
    val seasons: ImmutableList<SeasonItem> = persistentListOf(),
    val isViewAllSeasonsButtonVisible: Boolean = false,
) : ViewState
