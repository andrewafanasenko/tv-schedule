package com.example.tvschedule.data.schedule.mapper

import com.example.tvschedule.data.search.mapper.ShowMapper
import com.example.tvschedule.data.util.ModelUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Test


class ScheduleMapperTest {

    private val scheduleMapperTest = ScheduleMapper(ShowMapper())

//    @Test
//    fun `response to domain model returns valid model`() {
//        assertThat(scheduleMapperTest.map(ModelUtil.scheduleResponse))
//            .isEqualTo(ModelUtil.schedule)
//    }
}
