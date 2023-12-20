package com.example.tvschedule.data.search.repository

import com.example.tvschedule.data.search.mapper.ShowMapper
import com.example.tvschedule.data.search.source.local.SearchLocalDataSource
import com.example.tvschedule.data.search.source.remote.SearchRemoteDataSource
import com.example.tvschedule.data.util.ModelUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class SearchRepositoryTest {

    private val remoteDataSource = mock<SearchRemoteDataSource>()

    private val localDataSource = mock<SearchLocalDataSource>()

    private val mapper = mock<ShowMapper>()

    private val searchRepository = SearchRepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource,
        mapper = mapper
    )

    @Test
    fun `search show success`() = runTest {
        val showsResponse = ModelUtil.showsSearchResponse.mapNotNull { it.show }
        whenever(remoteDataSource.searchShow("")).thenReturn(ModelUtil.showsSearchResponse)
        whenever(mapper.mapList(showsResponse))
            .thenReturn(ModelUtil.shows)
        val result = searchRepository.searchShow("")
        verify(remoteDataSource, Mockito.times(1))
            .searchShow("")
        verify(mapper, Mockito.times(1)).mapList(showsResponse)
        assertThat(result).isEqualTo(ModelUtil.shows)
    }

    @Test
    fun `search show failed`() = runTest {
        val exception = IllegalStateException("Failed to search show")
        whenever(remoteDataSource.searchShow("")).thenThrow(exception)
        runCatching {
            searchRepository.searchShow("")
        }.onFailure { e ->
            verify(remoteDataSource, Mockito.times(1)).searchShow("")
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `get show from local success`() = runTest {
        whenever(localDataSource.shows)
            .thenReturn(hashMapOf(ModelUtil.showId to ModelUtil.showResponse))
        whenever(mapper.map(ModelUtil.showResponse)).thenReturn(ModelUtil.show)
        val result = searchRepository.getShowFromCache(ModelUtil.showId)
        verify(localDataSource, Mockito.times(1)).shows
        verify(mapper, Mockito.times(1)).map(ModelUtil.showResponse)
        assertThat(result).isEqualTo(ModelUtil.show)
    }

    @Test
    fun `get show from local failed`() = runTest {
        val exception = IllegalStateException("Failed to get show from local")
        whenever(localDataSource.shows).thenThrow(exception)
        runCatching {
            searchRepository.getShowFromCache(ModelUtil.showId)
        }.onFailure { e ->
            verify(localDataSource, Mockito.times(1)).shows
            assertThat(e).isEqualTo(exception)
        }
    }
}
