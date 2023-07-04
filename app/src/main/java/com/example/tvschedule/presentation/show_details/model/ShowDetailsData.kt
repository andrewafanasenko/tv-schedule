package com.example.tvschedule.presentation.show_details.model

import com.example.tvschedule.domain.search.model.Show


data class ShowDetailsData(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val show: Show? = null,
    val isFavorite: Boolean = false
)
