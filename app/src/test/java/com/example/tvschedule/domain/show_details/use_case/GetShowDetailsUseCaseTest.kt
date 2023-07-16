package com.example.tvschedule.domain.show_details.use_case

import com.example.tvschedule.data.util.ModelUtil.show
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.example.tvschedule.domain.schedule.repository.ScheduleRepository
import com.example.tvschedule.domain.search.repository.SearchRepository
import com.example.tvschedule.domain.show_details.model.ShowDetails
import com.example.tvschedule.domain.show_details.repository.ShowDetailsRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class GetShowDetailsUseCaseTest {

    private val showDetailsRepository = mock<ShowDetailsRepository>()
    private val scheduleRepository = mock<ScheduleRepository>()
    private val searchRepository = mock<SearchRepository>()
    private val favoriteRepository = mock<FavoriteRepository>()

    private val getShowDetailsUseCase = GetShowDetailsUseCase(
        showDetailsRepository = showDetailsRepository,
        scheduleRepository = scheduleRepository,
        searchRepository = searchRepository,
        favoriteRepository = favoriteRepository,
        ioDispatcher = Dispatchers.IO
    )

    @Test
    fun `get favorite show details success`() = runTest {
        var showDetails = ShowDetails(show, false)
        whenever(favoriteRepository.getFavoriteShow(show.id)).thenReturn(show).also {
            showDetails = showDetails.copy(isFavorite = true)
        }
        whenever(showDetailsRepository.getShowDetails(show.id)).thenReturn(show)
        if (showDetails.isFavorite) {
            whenever(favoriteRepository.updateFavorite(show)).thenReturn(Unit)
        }
        val result = getShowDetailsUseCase.invoke(show.id)
        result.collect { res ->
            verify(favoriteRepository, times(1)).getFavoriteShow(show.id)
            verify(showDetailsRepository, times(1)).getShowDetails(show.id)
            if (showDetails.isFavorite) {
                verify(favoriteRepository, times(1)).updateFavorite(show)
            }
            assertThat(res).isEqualTo(showDetails)
        }
    }

    @Test
    fun `get favorite show details failed`() = runTest {
        val exception = IllegalStateException("Failed to get favorite show")
        whenever(favoriteRepository.getFavoriteShow(show.id)).thenThrow(exception)
        whenever(showDetailsRepository.getShowDetails(show.id)).thenThrow(exception)
        val result = getShowDetailsUseCase.invoke(show.id)
        result.catch { e ->
            verify(favoriteRepository, times(1)).getFavoriteShow(show.id)
            verify(showDetailsRepository, times(1)).getShowDetails(show.id)
            assertThat(e).isEqualTo(exception)
        }

    }

    @Test
    fun `get schedule show details success`() = runTest {
        val showDetails = ShowDetails(show, false)
        whenever(scheduleRepository.getShowFromCache(show.id)).thenReturn(show)
        whenever(showDetailsRepository.getShowDetails(show.id)).thenReturn(show)
        val result = getShowDetailsUseCase.invoke(show.id)
        result.collect { res ->
            verify(scheduleRepository, times(1)).getShowFromCache(show.id)
            verify(showDetailsRepository, times(1)).getShowDetails(show.id)
            assertThat(res).isEqualTo(showDetails)
        }
    }

    @Test
    fun `get schedule show details failed`() = runTest {
        val exception = IllegalStateException("Failed to get schedule show")
        whenever(scheduleRepository.getShowFromCache(show.id)).thenThrow(exception)
        whenever(showDetailsRepository.getShowDetails(show.id)).thenThrow(exception)
        val result = getShowDetailsUseCase.invoke(show.id)
        result.catch { e ->
            verify(scheduleRepository, times(1)).getShowFromCache(show.id)
            verify(showDetailsRepository, times(1)).getShowDetails(show.id)
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `get search show details success`() = runTest {
        val showDetails = ShowDetails(show, false)
        whenever(searchRepository.getShowFromCache(show.id)).thenReturn(show)
        whenever(showDetailsRepository.getShowDetails(show.id)).thenReturn(show)
        val result = getShowDetailsUseCase.invoke(show.id)
        result.collect { res ->
            verify(searchRepository, times(1)).getShowFromCache(show.id)
            verify(showDetailsRepository, times(1)).getShowDetails(show.id)
            assertThat(res).isEqualTo(showDetails)
        }
    }

    @Test
    fun `get search show details failed`() = runTest {
        val exception = IllegalStateException("Failed to get search show")
        whenever(searchRepository.getShowFromCache(show.id)).thenThrow(exception)
        whenever(showDetailsRepository.getShowDetails(show.id)).thenThrow(exception)
        val result = getShowDetailsUseCase.invoke(show.id)
        result.catch { e ->
            verify(searchRepository, times(1)).getShowFromCache(show.id)
            verify(showDetailsRepository, times(1)).getShowDetails(show.id)
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `get only remote show details success`() = runTest {
        val showDetails = ShowDetails(show, false)
        whenever(favoriteRepository.getFavoriteShow(show.id)).thenReturn(null)
        whenever(scheduleRepository.getShowFromCache(show.id)).thenReturn(null)
        whenever(searchRepository.getShowFromCache(show.id)).thenReturn(null)
        whenever(showDetailsRepository.getShowDetails(show.id)).thenReturn(show)
        val result = getShowDetailsUseCase.invoke(show.id)
        result.collect { res ->
            verify(favoriteRepository, times(1)).getFavoriteShow(show.id)
            verify(scheduleRepository, times(1)).getShowFromCache(show.id)
            verify(searchRepository, times(1)).getShowFromCache(show.id)
            verify(showDetailsRepository, times(1)).getShowDetails(show.id)
            assertThat(res).isEqualTo(showDetails)
        }
    }

    @Test
    fun `get only remote show details failed`() = runTest {
        val exception = IllegalStateException("Failed to get show")
        whenever(favoriteRepository.getFavoriteShow(show.id)).thenReturn(null)
        whenever(scheduleRepository.getShowFromCache(show.id)).thenReturn(null)
        whenever(searchRepository.getShowFromCache(show.id)).thenReturn(null)
        whenever(showDetailsRepository.getShowDetails(show.id)).thenThrow(exception)
        val result = getShowDetailsUseCase.invoke(show.id)
        result.catch { e ->
            verify(favoriteRepository, times(1)).getFavoriteShow(show.id)
            verify(scheduleRepository, times(1)).getShowFromCache(show.id)
            verify(searchRepository, times(1)).getShowFromCache(show.id)
            verify(showDetailsRepository, times(1)).getShowDetails(show.id)
            assertThat(e).isEqualTo(exception)
        }
    }
}
