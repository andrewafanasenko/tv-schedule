package com.example.tvschedule.domain.schedule.use_case

import com.example.tvschedule.data.util.ModelUtil.schedule
import com.example.tvschedule.domain.schedule.repository.ScheduleRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate


class GetScheduleUseCaseTest {

    private val scheduleRepository = Mockito.mock<ScheduleRepository>()

    private val getScheduleUseCase = GetScheduleUseCase(
        scheduleRepository = scheduleRepository,
        ioDispatcher = Dispatchers.IO
    )

    @Test
    fun `get schedule success`() = runTest {
        val date = LocalDate.now()
        val schedules = listOf(schedule)
        whenever(scheduleRepository.getSchedule(date)).thenReturn(schedules)
        val result = getScheduleUseCase.invoke(date)
        assertThat(result).isEqualTo(Result.success(schedules))
        verify(scheduleRepository, times(1)).getSchedule(date)
    }

    @Test
    fun `get schedule failed`() = runTest {
        val date = LocalDate.now()
        val exception = IllegalStateException("Failed to get schedule")
        whenever(scheduleRepository.getSchedule(date)).thenThrow(exception)
        val result = getScheduleUseCase.invoke(date)
        assertThat(result.isFailure).isEqualTo(true)
        verify(scheduleRepository, times(1)).getSchedule(date)
    }

}
