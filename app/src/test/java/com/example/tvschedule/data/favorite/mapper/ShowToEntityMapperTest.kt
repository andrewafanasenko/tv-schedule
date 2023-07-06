package com.example.tvschedule.data.favorite.mapper

import com.example.tvschedule.data.util.ModelUtil
import com.google.common.truth.Truth
import org.junit.Test


internal class ShowToEntityMapperTest {

    private val showToEntityMapper = ShowToEntityMapper()

    @Test
    fun `domain model to entity model returns valid model`() {
        Truth.assertThat(showToEntityMapper.map(ModelUtil.show))
            .isEqualTo(ModelUtil.showEntity)
    }
}
