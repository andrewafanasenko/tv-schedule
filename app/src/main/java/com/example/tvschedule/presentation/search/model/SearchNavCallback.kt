package com.example.tvschedule.presentation.search.model


sealed interface SearchNavCallback {
    data class ShowDetails(val showId: Long) : SearchNavCallback
}
