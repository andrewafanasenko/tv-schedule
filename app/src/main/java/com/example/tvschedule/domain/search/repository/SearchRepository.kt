package com.example.tvschedule.domain.search.repository

import com.example.tvschedule.domain.show_details.model.Show

interface SearchRepository {

    suspend fun searchShow(query: String): List<Show>

    suspend fun getShowFromCache(showId: Long): Show?
}
