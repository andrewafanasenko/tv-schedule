package com.example.tvschedule.data.schedule.repository

import com.example.tvschedule.data.schedule.source.local.ScheduleLocalDataSource
import com.example.tvschedule.data.schedule.mapper.ScheduleMapper
import com.example.tvschedule.data.schedule.source.remote.ScheduleRemoteDataSource
import com.example.tvschedule.data.search.mapper.ShowMapper
import com.example.tvschedule.domain.schedule.model.Schedule
import com.example.tvschedule.domain.schedule.repository.ScheduleRepository
import com.example.tvschedule.domain.show_details.model.Show
import java.time.LocalDate
import javax.inject.Inject


class ScheduleRepositoryImpl @Inject constructor(
    private val localDataSource: ScheduleLocalDataSource,
    private val remoteDataSource: ScheduleRemoteDataSource,
    private val mapper: ScheduleMapper,
    private val showMapper: ShowMapper
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

    override suspend fun getShowFromCache(showId: Long): Show? {
        val localShow = localDataSource.schedules.values
            .flatten()
            .mapNotNull { it.show }
            .firstOrNull { it.id == showId }
        return localShow?.let { showMapper.map(localShow) }
    }

}
