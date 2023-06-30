package com.example.tvschedule.domain.search.use_case

import com.example.tvschedule.di.IoDispatcher
import com.example.tvschedule.domain.search.model.Show
import com.example.tvschedule.domain.search.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class SearchShowUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(query: String): Result<List<Show>> = with(ioDispatcher) {
        runCatching {
            searchRepository.searchShow(query)
        }
    }
}
