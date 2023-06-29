package com.example.tvschedule.data.schedule.source.local

import com.example.tvschedule.data.schedule.api.model.ScheduleResponse
import java.time.LocalDate


interface ScheduleLocalDataSource {

    val schedules: HashMap<LocalDate, List<ScheduleResponse>>

}
