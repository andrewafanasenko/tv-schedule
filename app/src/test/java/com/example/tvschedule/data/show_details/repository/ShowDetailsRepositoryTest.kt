package com.example.tvschedule.data.show_details.repository

import com.example.tvschedule.data.search.mapper.ShowMapper
import com.example.tvschedule.data.show_details.api.ShowDetailsApi
import com.example.tvschedule.data.util.ModelUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class ShowDetailsRepositoryTest {

    private val api = mock<ShowDetailsApi>()

    private val showMapper = mock<ShowMapper>()

    private val showDetailsRepository = ShowDetailsRepositoryImpl(
        api = api,
        showMapper = showMapper
    )

    @Test
    fun `get show details success`() = runTest {
        whenever(api.getShowDetails(ModelUtil.showId)).thenReturn(ModelUtil.showResponse)
        whenever(showMapper.map(ModelUtil.showResponse)).thenReturn(ModelUtil.show)
        val result = showDetailsRepository.getShowDetails(ModelUtil.showId)
        verify(api, Mockito.times(1)).getShowDetails(ModelUtil.showId)
        verify(showMapper, Mockito.times(1)).map(ModelUtil.showResponse)
        assertThat(result).isEqualTo(ModelUtil.show)
    }

    @Test
    fun `get show details failed`() = runTest {
        val exception = IllegalStateException("Failed to get show details")
        whenever(api.getShowDetails(ModelUtil.showId)).thenThrow(exception)
        runCatching {
            showDetailsRepository.getShowDetails(ModelUtil.showId)
        }.onFailure { e ->
            verify(api, Mockito.times(1)).getShowDetails(ModelUtil.showId)
            assertThat(e).isEqualTo(exception)
        }
    }
}
