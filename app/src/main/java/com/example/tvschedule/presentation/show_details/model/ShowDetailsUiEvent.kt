package com.example.tvschedule.presentation.show_details.model

import com.example.tvschedule.presentation.common.ViewEvent


sealed interface ShowDetailsUiEvent : ViewEvent {
    object OnFavoriteClick : ShowDetailsUiEvent
    object OnShowAllCastClick : ShowDetailsUiEvent
    object OnShowAllSeasonsClick : ShowDetailsUiEvent
}
