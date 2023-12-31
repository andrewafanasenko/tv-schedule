package com.example.tvschedule.presentation.search.model

import com.example.tvschedule.presentation.common.ViewEvent


sealed interface SearchUiEvent : ViewEvent {
    data class OnQueryChange(val query: String) : SearchUiEvent
    data object Retry : SearchUiEvent
    data class OnFavoriteClick(val showId: Long, val isFavorite: Boolean) : SearchUiEvent
}
