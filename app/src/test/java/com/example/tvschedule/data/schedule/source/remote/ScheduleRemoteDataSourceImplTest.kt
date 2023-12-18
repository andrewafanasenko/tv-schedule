package com.example.tvschedule.data.schedule.source.remote

import com.example.tvschedule.data.schedule.api.ScheduleApi
import com.example.tvschedule.data.util.ModelUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate


class ScheduleRemoteDataSourceTest {

    private val api =  mock<ScheduleApi>()

    private val scheduleRemoteDataSource = ScheduleRemoteDataSourceImpl(api)

    @Test
    fun `get schedule success`() = runTest {
        whenever(api.getSchedule(LocalDate.now())).thenReturn(ModelUtil.schedulesResponse)
        val result = scheduleRemoteDataSource.getSchedule(LocalDate.now())
        verify(api, Mockito.times(1)).getSchedule(LocalDate.now())
        assertThat(result).isEqualTo(ModelUtil.schedulesResponse)
    }

    @Test
    fun `get schedule failed`() = runTest {
        val exception = IllegalStateException("Failed to get schedule")
        whenever(api.getSchedule(LocalDate.now())).thenThrow(exception)
        runCatching {
            scheduleRemoteDataSource.getSchedule(LocalDate.now())
        }.onFailure { e ->
            verify(api, Mockito.times(1)).getSchedule(LocalDate.now())
            assertThat(e).isEqualTo(exception)
        }
    }
}
