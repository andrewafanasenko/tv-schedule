package com.example.tvschedule.data.search.source.remote

import com.example.tvschedule.data.search.api.SearchApi
import com.example.tvschedule.data.util.ModelUtil
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class SearchRemoteDataSourceTest {

    private val api =  mock<SearchApi>()

    private val searchRemoteDataSource = SearchRemoteDataSourceImpl(api)

    @Test
    fun `search show success`() = runTest {
        whenever(api.searchShow("")).thenReturn(ModelUtil.showsSearchResponse)
        val result = searchRemoteDataSource.searchShow("")
        verify(api, Mockito.times(1)).searchShow("")
        Truth.assertThat(result).isEqualTo(ModelUtil.showsSearchResponse)
    }

    @Test
    fun `search show failed`() = runTest {
        val exception = IllegalStateException("Failed to search show")
        whenever(api.searchShow("")).thenThrow(exception)
        runCatching {
            searchRemoteDataSource.searchShow("")
        }.onFailure { e ->
            verify(api, Mockito.times(1)).searchShow("")
            Truth.assertThat(e).isEqualTo(exception)
        }
    }
}
