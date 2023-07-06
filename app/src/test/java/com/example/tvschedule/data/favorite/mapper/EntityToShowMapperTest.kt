package com.example.tvschedule.data.favorite.mapper

import com.example.tvschedule.data.util.ModelUtil
import org.junit.Test
import com.google.common.truth.Truth.assertThat


class EntityToShowMapperTest {

    private val entityToShowMapper = EntityToShowMapper()

    @Test
    fun `entity to domain model returns valid model`() {
        assertThat(entityToShowMapper.map(ModelUtil.showEntity))
            .isEqualTo(ModelUtil.showFromEntity)
    }
}
