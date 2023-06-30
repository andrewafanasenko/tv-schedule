package com.example.tvschedule.domain.schedule.use_case

import com.example.tvschedule.di.IoDispatcher
import com.example.tvschedule.domain.schedule.model.Schedule
import com.example.tvschedule.domain.schedule.repository.ScheduleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject


class GetScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        date: LocalDate
    ): Result<List<Schedule>> = withContext(ioDispatcher) {
        runCatching {
            scheduleRepository.getSchedule(date)
        }
    }
}
