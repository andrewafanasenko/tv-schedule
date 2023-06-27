package com.example.tvschedule.data.schedule.source.local

import com.example.tvschedule.data.api.model.ScheduleResponse
import java.time.LocalDate


interface ScheduleLocalDataSource {

    var schedules: HashMap<LocalDate, List<ScheduleResponse>>

}
