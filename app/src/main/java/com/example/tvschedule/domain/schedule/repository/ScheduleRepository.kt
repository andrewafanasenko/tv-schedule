package com.example.tvschedule.domain.schedule.repository

import com.example.tvschedule.domain.schedule.model.Schedule
import java.time.LocalDate


interface ScheduleRepository {

    suspend fun getSchedule(date: LocalDate): List<Schedule>
}
