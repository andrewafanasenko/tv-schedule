package com.example.tvschedule.data.api

import com.example.tvschedule.data.api.model.ScheduleResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate


interface ScheduleApi {

    @GET("/schedule")
    suspend fun getSchedule(@Query("date") date: LocalDate): List<ScheduleResponse>
}
