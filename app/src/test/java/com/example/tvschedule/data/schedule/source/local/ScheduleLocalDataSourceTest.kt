package com.example.tvschedule.data.schedule.source.local

import com.example.tvschedule.data.util.ModelUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate


class ScheduleLocalDataSourceTest {

    private val scheduleLocalDataSource = ScheduleLocalDataSourceImpl()

    @Test
    fun `get from local data source success`() = runTest {
        scheduleLocalDataSource.schedules[LocalDate.now()] = ModelUtil.schedulesResponse
        val result = scheduleLocalDataSource.schedules[LocalDate.now()]
        assertThat(result).isEqualTo(ModelUtil.schedulesResponse)
    }

    @Test
    fun `get from local data source failed`() = runTest {
        val exception = IllegalStateException("Failed to get from local data source")
        scheduleLocalDataSource.schedules[LocalDate.now().plusDays(1)] = ModelUtil.schedulesResponse
        runCatching {
            scheduleLocalDataSource.schedules[LocalDate.now()]
        }.onFailure { e ->
            assertThat(e).isEqualTo(exception)
        }
    }
}
