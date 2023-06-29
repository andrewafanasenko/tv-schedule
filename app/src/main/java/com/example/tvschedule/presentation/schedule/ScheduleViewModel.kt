package com.example.tvschedule.presentation.schedule

import androidx.lifecycle.viewModelScope
import com.example.tvschedule.domain.schedule.use_case.GetScheduleUseCase
import com.example.tvschedule.presentation.common.BaseViewModel
import com.example.tvschedule.presentation.schedule.model.ScheduleData
import com.example.tvschedule.presentation.schedule.model.ScheduleItem
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
import javax.inject.Inject


@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase
) : BaseViewModel<ScheduleUiEvent, ScheduleUiState>() {

    private var loadScheduleJob: Job? = null

    private val scheduleData = MutableStateFlow(ScheduleData())

    init {
        loadSchedule()
    }

    override val viewState: StateFlow<ScheduleUiState> = scheduleData.map { data ->
        ScheduleUiState(
            isLoading = data.isLoading,
            isError = data.isError,
            selectedDate = data.selectedDate,
            schedule = data.schedule.map { schedule ->
                ScheduleItem(
                    id = schedule.id,
                    name = schedule.show.showName,
                    episodeName = schedule.episodeName,
                    summary = schedule.summary,
                    coverUrl = schedule.coverUrl,
                    time = schedule.airDateTime?.toLocalTime()?.toString().orEmpty(),
                    durationMin = schedule.runtime,
                    rating = schedule.rating,
                    season = schedule.seasonNumber,
                    episode = schedule.episodeNumber
                )
            }
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ScheduleUiState())

    override fun handleEvent(event: ScheduleUiEvent) {
        when (event) {
            is ScheduleUiEvent.SelectDate -> {
                scheduleData.update { it.copy(selectedDate = event.date) }
                loadSchedule()
            }

            ScheduleUiEvent.Retry -> {
                loadSchedule()
            }
        }
    }

    private fun loadSchedule() {
        loadScheduleJob?.cancel()
        scheduleData.update { it.copy(isLoading = true) }
        loadScheduleJob = viewModelScope.launch {
            getScheduleUseCase.invoke(scheduleData.value.selectedDate)
                .onSuccess { result ->
                    scheduleData.update {
                        it.copy(
                            isLoading = false,
                            isError = false,
                            schedule = result
                        )
                    }
                }
                .onFailure {
                    scheduleData.update { it.copy(isLoading = false, isError = true) }
                }
        }
    }
}
