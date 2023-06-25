package com.example.tvschedule.data.schedule.repository

import com.example.tvschedule.data.schedule.local.ScheduleLocalDataSource
import com.example.tvschedule.data.schedule.mapper.ScheduleMapper
import com.example.tvschedule.data.schedule.remote.ScheduleRemoteDataSource
import com.example.tvschedule.domain.schedule.model.Schedule
import com.example.tvschedule.domain.schedule.repository.ScheduleRepository
import java.time.LocalDate
import javax.inject.Inject


class ScheduleRepositoryImpl @Inject constructor(
    private val localDataSource: ScheduleLocalDataSource,
    private val remoteDataSource: ScheduleRemoteDataSource,
    private val mapper: ScheduleMapper
) : ScheduleRepository {

    override suspend fun getSchedule(date: LocalDate): List<Schedule> {
        val cachedSchedule = localDataSource.schedules[date].orEmpty()
        if (cachedSchedule.isNotEmpty()) {
            return mapper.mapList(cachedSchedule)
        }
        val schedule = remoteDataSource.getSchedule(date)
        localDataSource.schedules[date] = schedule
        return mapper.mapList(schedule)
    }

}
