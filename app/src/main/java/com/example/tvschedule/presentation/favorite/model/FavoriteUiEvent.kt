package com.example.tvschedule.presentation.favorite.model

import com.example.tvschedule.presentation.common.ViewEvent


sealed interface FavoriteUiEvent : ViewEvent {
    data class OnQueryChange(val query: String) : FavoriteUiEvent
    object Retry : FavoriteUiEvent
    data class OnFavoriteClick(val showId: Long) : FavoriteUiEvent
}
