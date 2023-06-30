package com.example.tvschedule.domain.search.repository

import com.example.tvschedule.domain.search.model.Show

interface SearchRepository {

    suspend fun searchShow(query: String): List<Show>
}
