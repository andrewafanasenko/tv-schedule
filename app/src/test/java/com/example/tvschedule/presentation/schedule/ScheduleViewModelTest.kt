package com.example.tvschedule.presentation.schedule

import com.example.tvschedule.MainDispatcherRule
import com.example.tvschedule.data.util.ModelUtil
import com.example.tvschedule.domain.schedule.use_case.GetScheduleUseCase
import com.example.tvschedule.presentation.schedule.model.ScheduleUiEvent
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class ScheduleViewModelTest {

    private val getScheduleUseCase = mock<GetScheduleUseCase>()

    private lateinit var viewModel: ScheduleViewModel

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    @Test
    fun `load schedule success`() = runTest {
        whenever(getScheduleUseCase.invoke(any()))
            .thenReturn(Result.success(ModelUtil.schedules))
        viewModel = ScheduleViewModel(getScheduleUseCase)
        verify(getScheduleUseCase, times(1)).invoke(any())
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
        assertThat(state.schedule).isNotEmpty()
    }

    @Test
    fun `load schedule failed`() = runTest {
        whenever(getScheduleUseCase.invoke(any()))
            .thenReturn(Result.failure(IllegalStateException("Failed to load schedule")))
        viewModel = ScheduleViewModel(getScheduleUseCase)
        verify(getScheduleUseCase, times(1)).invoke(any())
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isTrue()
        assertThat(state.schedule).isEmpty()
    }

    @Test
    fun `handle select date event success`() = runTest {
        whenever(getScheduleUseCase.invoke(any()))
            .thenReturn(Result.success(ModelUtil.schedules))
        viewModel = ScheduleViewModel(getScheduleUseCase)
        whenever(viewModel.setEvent(ScheduleUiEvent.SelectDate(ModelUtil.scheduleDate)))
            .thenReturn(Unit)
        verify(getScheduleUseCase, times(1)).invoke(any())
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
        assertThat(state.schedule).isNotEmpty()
        assertThat(state.selectedDate).isEqualTo(ModelUtil.scheduleDate)
    }

    @Test
    fun `handle retry event success`() = runTest {
        whenever(getScheduleUseCase.invoke(any()))
            .thenReturn(Result.success(ModelUtil.schedules))
        viewModel = ScheduleViewModel(getScheduleUseCase)
        whenever(viewModel.setEvent(ScheduleUiEvent.Retry)).thenReturn(Unit)
        verify(getScheduleUseCase, times(1)).invoke(any())
        val state = viewModel.viewState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isError).isFalse()
        assertThat(state.schedule).isNotEmpty()
    }
}
