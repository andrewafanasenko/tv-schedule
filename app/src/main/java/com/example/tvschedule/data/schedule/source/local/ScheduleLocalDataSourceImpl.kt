package com.example.tvschedule.data.schedule.source.local

import com.example.tvschedule.data.api.model.ScheduleResponse
import java.time.LocalDate
import javax.inject.Inject


class ScheduleLocalDataSourceImpl @Inject constructor() : ScheduleLocalDataSource {

    override var schedules: HashMap<LocalDate, List<ScheduleResponse>> = hashMapOf()

}
