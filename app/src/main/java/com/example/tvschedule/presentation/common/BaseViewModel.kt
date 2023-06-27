package com.example.tvschedule.presentation.common

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
interface ViewState

interface ViewEvent

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState> : ViewModel() {

    abstract val viewState: StateFlow<UiState>

    fun setEvent(event: Event) {
        handleEvent(event)
    }

    protected abstract fun handleEvent(event: Event)
}
