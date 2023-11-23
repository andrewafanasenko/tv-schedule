package com.example.tvschedule.data.search.mapper

import com.example.tvschedule.data.util.ModelUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Test


class ShowMapperTest {

    private val showMapper = ShowMapper()

    @Test
    fun `response to domain model returns valid model`() {
        assertThat(showMapper.map(ModelUtil.showResponse))
            .isEqualTo(ModelUtil.show)
    }
}
