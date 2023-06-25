package com.example.tvschedule.presentation.schedule

import androidx.lifecycle.viewModelScope
import com.example.tvschedule.domain.schedule.use_case.GetScheduleUseCase
import com.example.tvschedule.presentation.common.BaseViewModel
import com.example.tvschedule.presentation.schedule.model.ScheduleUiEvent
import com.example.tvschedule.presentation.schedule.model.ScheduleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase
) : BaseViewModel<ScheduleUiEvent, ScheduleUiState>() {

    private var loadScheduleJob: Job? = null

    init {
        loadSchedule(LocalDate.now())
    }

    override val initialState: ScheduleUiState = ScheduleUiState()

    override fun handleEvent(event: ScheduleUiEvent) {
        when (event) {
            is ScheduleUiEvent.SelectDate -> loadSchedule(event.date)
        }
    }

    private fun loadSchedule(date: LocalDate) {
        loadScheduleJob?.cancel()
        loadScheduleJob = viewModelScope.launch {
            getScheduleUseCase.invoke(date)
                .onSuccess {  }
                .onFailure {  }
        }
    }
}
