package com.example.tvschedule.domain.show_details.repository

import com.example.tvschedule.domain.show_details.model.Show


interface ShowDetailsRepository {

    suspend fun getShowDetails(showId: Long): Show
}
