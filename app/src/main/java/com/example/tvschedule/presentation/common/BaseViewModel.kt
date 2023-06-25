package com.example.tvschedule.presentation.common

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
interface ViewState

interface ViewEvent

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState> : ViewModel() {

    protected abstract val initialState: UiState

    private val _viewState by lazy { MutableStateFlow(initialState) }
    val viewState: StateFlow<UiState> get() = _viewState

    fun setEvent(event: Event) {
        handleEvent(event)
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected abstract fun handleEvent(event: Event)
}
