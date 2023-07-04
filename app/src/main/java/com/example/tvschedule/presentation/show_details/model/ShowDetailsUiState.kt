package com.example.tvschedule.presentation.show_details.model

import com.example.tvschedule.presentation.common.ViewState


data class ShowDetailsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val coverUrl: String = "",
    val isFavorite: Boolean = false
):ViewState
