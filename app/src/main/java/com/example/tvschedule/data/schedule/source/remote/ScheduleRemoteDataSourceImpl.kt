package com.example.tvschedule.data.schedule.source.remote

import com.example.tvschedule.data.schedule.api.ScheduleApi
import com.example.tvschedule.data.schedule.api.model.ScheduleResponse
import java.time.LocalDate
import javax.inject.Inject


class ScheduleRemoteDataSourceImpl @Inject constructor(
    private val api: ScheduleApi
): ScheduleRemoteDataSource {

    override suspend fun getSchedule(
        date: LocalDate
    ): List<ScheduleResponse> = api.getSchedule(date)
}
