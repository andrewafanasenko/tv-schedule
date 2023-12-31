package com.example.tvschedule.presentation.search.model

import com.example.tvschedule.domain.show_details.model.Show


data class SearchData(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchQuery: String = "",
    val shows: List<Show> = emptyList(),
    val favoriteShowsIds: List<Long> = emptyList()
)
