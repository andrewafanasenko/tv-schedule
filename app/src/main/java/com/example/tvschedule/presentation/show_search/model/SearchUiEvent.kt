package com.example.tvschedule.presentation.show_search.model

import com.example.tvschedule.presentation.common.ViewEvent


sealed interface SearchUiEvent : ViewEvent {
    data class OnQueryChange(val query: String) : SearchUiEvent

}
