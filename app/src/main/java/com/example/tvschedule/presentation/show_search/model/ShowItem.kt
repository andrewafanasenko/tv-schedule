package com.example.tvschedule.presentation.show_search.model

data class ShowItem(
    val id: Long,
    val name: String,
    val summary: String,
    val coverUrl: String,
    val rating: Double?,
    val genres: List<String>,
    val isFavourite: Boolean,
)
