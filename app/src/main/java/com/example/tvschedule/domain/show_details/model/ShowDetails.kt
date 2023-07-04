package com.example.tvschedule.domain.show_details.model

import com.example.tvschedule.domain.search.model.Show


data class ShowDetails(
    val show: Show,
    val isFavorite: Boolean
)
