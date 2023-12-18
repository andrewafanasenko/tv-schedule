package com.example.tvschedule.data.schedule.repository

import com.example.tvschedule.data.schedule.mapper.ScheduleMapper
import com.example.tvschedule.data.schedule.source.local.ScheduleLocalDataSource
import com.example.tvschedule.data.schedule.source.remote.ScheduleRemoteDataSource
import com.example.tvschedule.data.search.mapper.ShowMapper
import com.example.tvschedule.data.util.ModelUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class ScheduleRepositoryTest {

    private val localDataSource = mock<ScheduleLocalDataSource>()
    private val remoteDataSource = mock<ScheduleRemoteDataSource>()
    private val mapper = mock<ScheduleMapper>()
    private val showMapper = mock<ShowMapper>()

    private val scheduleRepository = ScheduleRepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource,
        mapper = mapper,
        showMapper = showMapper
    )

    @Test
    fun `get schedule from remote success`() = runTest {
        whenever(remoteDataSource.getSchedule(ModelUtil.scheduleDate))
            .thenReturn(ModelUtil.schedulesResponse)
        whenever(mapper.mapList(ModelUtil.schedulesResponse)).thenReturn(ModelUtil.schedules)
        val result = scheduleRepository.getSchedule(ModelUtil.scheduleDate)
        verify(remoteDataSource, Mockito.times(1))
            .getSchedule(ModelUtil.scheduleDate)
        verify(mapper, Mockito.times(1)).mapList(ModelUtil.schedulesResponse)
        assertThat(result).isEqualTo(ModelUtil.schedules)
    }

    @Test
    fun `get schedule from remote failed`() = runTest {
        val exception = IllegalStateException("Failed to get schedule from remote")
        whenever(remoteDataSource.getSchedule(ModelUtil.scheduleDate)).thenThrow(exception)
        runCatching {
            scheduleRepository.getSchedule(ModelUtil.scheduleDate)
        }.onFailure { e ->
            verify(remoteDataSource, Mockito.times(1))
                .getSchedule(ModelUtil.scheduleDate)
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `get schedule and return local if exist success`() = runTest {
        whenever(localDataSource.schedules)
            .thenReturn(hashMapOf(ModelUtil.scheduleDate to ModelUtil.schedulesResponse))
        whenever(mapper.mapList(ModelUtil.schedulesResponse)).thenReturn(ModelUtil.schedules)
        val result = scheduleRepository.getSchedule(ModelUtil.scheduleDate)
        verify(localDataSource, Mockito.times(1)).schedules
        verify(remoteDataSource, Mockito.times(0))
            .getSchedule(ModelUtil.scheduleDate)
        verify(mapper, Mockito.times(1)).mapList(ModelUtil.schedulesResponse)
        assertThat(result).isEqualTo(ModelUtil.schedules)
    }

    @Test
    fun `get show from local success`() = runTest {
        whenever(localDataSource.schedules)
            .thenReturn(hashMapOf(ModelUtil.scheduleDate to ModelUtil.schedulesResponse))
        whenever(showMapper.map(ModelUtil.showResponse)).thenReturn(ModelUtil.show)
        val result = scheduleRepository.getShowFromCache(ModelUtil.showId)
        verify(localDataSource, Mockito.times(1)).schedules
        verify(showMapper, Mockito.times(1)).map(ModelUtil.showResponse)
        assertThat(result).isEqualTo(ModelUtil.show)
    }

    @Test
    fun `get show from local failed`() = runTest {
        val exception = IllegalStateException("Failed to get show from local")
        whenever(localDataSource.schedules).thenThrow(exception)
        runCatching {
            scheduleRepository.getShowFromCache(ModelUtil.showId)
        }.onFailure { e ->
            verify(localDataSource, Mockito.times(1)).schedules
            assertThat(e).isEqualTo(exception)
        }
    }
}
