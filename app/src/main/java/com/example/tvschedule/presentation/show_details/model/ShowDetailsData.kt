package com.example.tvschedule.presentation.show_details.model

import com.example.tvschedule.domain.show_details.model.Cast
import com.example.tvschedule.domain.show_details.model.Season
import com.example.tvschedule.domain.show_details.model.Show


data class ShowDetailsData(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val show: Show? = null,
    val isFavorite: Boolean = false,
    val cast: List<Cast> = emptyList(),
    val isAllCastVisible: Boolean = false,
    val seasons: List<Season> = emptyList(),
    val isAllSeasonsVisible: Boolean = false
) {
    val limitCast: Int
        get() = if (isAllCastVisible) Int.MAX_VALUE else MAX_ITEMS_COUNT

    val isViewAllCastButtonVisible: Boolean
        get() = if (isAllCastVisible) false else cast.size > MAX_ITEMS_COUNT

    val limitSeasons: Int
        get() = if (isAllSeasonsVisible) Int.MAX_VALUE else MAX_ITEMS_COUNT

    val isViewAllSeasonsButtonVisible: Boolean
        get() = if (isAllSeasonsVisible) false else seasons.size > MAX_ITEMS_COUNT

    companion object {
        private const val MAX_ITEMS_COUNT = 4
    }
}
