package com.example.tvschedule.presentation.favorite.model

sealed interface FavoriteNavCallback {
    data class ShowDetails(val showId: Long) : FavoriteNavCallback
}
