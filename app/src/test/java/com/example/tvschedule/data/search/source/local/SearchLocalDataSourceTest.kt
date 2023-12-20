package com.example.tvschedule.data.search.source.local

import com.example.tvschedule.data.util.ModelUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test


class SearchLocalDataSourceTest {

    private val searchLocalDataSource = SearchLocalDataSourceImpl()

    @Test
    fun `get from local data source success`() = runTest {
        searchLocalDataSource.shows[ModelUtil.showId] = ModelUtil.showResponse
        val result = searchLocalDataSource.shows[ModelUtil.showId]
        assertThat(result).isEqualTo(ModelUtil.showResponse)
    }

    @Test
    fun `get from local data source failed`() = runTest {
        val exception = IllegalStateException("Failed to get from local data source")
        searchLocalDataSource.shows[321] = ModelUtil.showResponse
        runCatching {
            searchLocalDataSource.shows[ModelUtil.showId]
        }.onFailure { e ->
            assertThat(e).isEqualTo(exception)
        }
    }
}
