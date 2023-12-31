package com.example.tvschedule.presentation.favorite.model

import com.example.tvschedule.domain.show_details.model.Show


data class FavoriteData(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchQuery: String = "",
    val shows: List<Show> = emptyList()
)
