package com.example.tvschedule.presentation.schedule

import androidx.lifecycle.viewModelScope
import com.example.tvschedule.domain.schedule.use_case.GetScheduleUseCase
import com.example.tvschedule.presentation.common.BaseViewModel
import com.example.tvschedule.presentation.schedule.model.ScheduleData
import com.example.tvschedule.presentation.schedule.model.ScheduleUiEvent
import com.example.tvschedule.presentation.schedule.model.ScheduleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase
) : BaseViewModel<ScheduleUiEvent, ScheduleUiState>() {

    private var loadScheduleJob: Job? = null

    private val scheduleData = MutableStateFlow(ScheduleData())

    init {
        loadSchedule(LocalDate.now())
    }

    override val viewState: StateFlow<ScheduleUiState> = scheduleData.map { data ->
        ScheduleUiState(data.isLoading, schedule = data.schedule.map { it.show.showName })
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ScheduleUiState())

    override fun handleEvent(event: ScheduleUiEvent) {
        when (event) {
            is ScheduleUiEvent.SelectDate -> loadSchedule(event.date)
        }
    }

    private fun loadSchedule(date: LocalDate) {
        loadScheduleJob?.cancel()
        scheduleData.update { it.copy(isLoading = true) }
        loadScheduleJob = viewModelScope.launch {
            getScheduleUseCase.invoke(date)
                .onSuccess { result ->
                    scheduleData.update {
                        it.copy(
                            isLoading = false,
                            isError = false,
                            schedule = result
                        )
                    }
                }
                .onFailure { throwable ->
                    scheduleData.update { it.copy(isLoading = false, isError = true) }
                }
        }
    }
}
