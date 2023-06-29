package com.example.tvschedule.data.schedule.mapper

import com.example.tvschedule.data.api.model.ScheduleResponse
import com.example.tvschedule.data.common.Mapper
import com.example.tvschedule.data.common.toLocalDateTime
import com.example.tvschedule.domain.schedule.model.Schedule
import com.example.tvschedule.domain.schedule.model.Show
import javax.inject.Inject


class ScheduleMapper @Inject constructor() : Mapper<ScheduleResponse, Schedule> {

    override fun map(input: ScheduleResponse): Schedule {
        return Schedule(
            id = input.id,
            episodeName = input.name.orEmpty(),
            summary = input.summary.orEmpty(),
            coverUrl = input.image?.medium ?: input.show?.image?.medium.orEmpty(),
            seasonNumber = input.season ?: 0,
            episodeNumber = input.number ?: 0,
            runtime = input.runtime ?: input.show?.averageRuntime ?: 0,
            airDateTime = input.airstamp.toLocalDateTime(),
            rating = input.rating?.average ?: input.show?.rating?.average,
            show = Show(
                id = input.show?.id ?: -1,
                showName = input.show?.name.orEmpty(),
                averageRuntime = input.show?.averageRuntime ?: 0
            )
        )
    }

}
