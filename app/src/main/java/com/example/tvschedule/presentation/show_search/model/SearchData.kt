package com.example.tvschedule.presentation.show_search.model

import com.example.tvschedule.domain.search.model.Show


data class SearchData(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchQuery: String = "",
    val shows: List<Show> = emptyList()
)
