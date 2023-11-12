package com.example.tvschedule.presentation.show_details.model

import com.example.tvschedule.presentation.common.ViewEvent


sealed interface ShowDetailsUiEvent : ViewEvent {
    data object OnFavoriteClick : ShowDetailsUiEvent
    data object OnShowAllCastClick : ShowDetailsUiEvent
    data object OnShowAllSeasonsClick : ShowDetailsUiEvent
}
