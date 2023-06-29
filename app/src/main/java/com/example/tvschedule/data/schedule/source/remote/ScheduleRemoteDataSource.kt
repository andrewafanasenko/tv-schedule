package com.example.tvschedule.data.schedule.source.remote

import com.example.tvschedule.data.schedule.api.model.ScheduleResponse
import java.time.LocalDate


interface ScheduleRemoteDataSource {

    suspend fun getSchedule(date: LocalDate): List<ScheduleResponse>

}
