package com.example.tvschedule.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState> : ViewModel() {

    abstract val viewState: StateFlow<UiState>

    fun setEvent(event: Event) {
        handleEvent(event)
    }

    protected abstract fun handleEvent(event: Event)
}
