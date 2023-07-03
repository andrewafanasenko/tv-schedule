package com.example.tvschedule.domain.schedule.repository

import com.example.tvschedule.domain.schedule.model.Schedule
import com.example.tvschedule.domain.search.model.Show
import java.time.LocalDate


interface ScheduleRepository {

    suspend fun getSchedule(date: LocalDate): List<Schedule>

    suspend fun getShowFromCache(showId: Long): Show?
}
