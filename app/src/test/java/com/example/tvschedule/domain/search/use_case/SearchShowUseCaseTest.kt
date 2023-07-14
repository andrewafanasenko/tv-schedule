package com.example.tvschedule.domain.search.use_case

import com.example.tvschedule.data.util.ModelUtil.show
import com.example.tvschedule.domain.search.repository.SearchRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class SearchShowUseCaseTest {

    private val searchRepository = Mockito.mock<SearchRepository>()

    private val getScheduleUseCase = SearchShowUseCase(
        searchRepository = searchRepository,
        ioDispatcher = Dispatchers.IO
    )

    @Test
    fun `search shows success`() = runTest {
        val query = "good"
        val shows = listOf(show)
        whenever(searchRepository.searchShow(query)).thenReturn(shows)
        val result = getScheduleUseCase.invoke(query)
        assertThat(result).isEqualTo(Result.success(shows))
        verify(searchRepository, times(1)).searchShow(query)
    }

    @Test
    fun `search shows failed`() = runTest {
        val query = "good"
        val exception = IllegalStateException("Search failed")
        whenever(searchRepository.searchShow(query)).thenThrow(exception)
        val result = getScheduleUseCase.invoke(query)
        assertThat(result.isFailure).isEqualTo(true)
        verify(searchRepository, times(1)).searchShow(query)
    }
}
