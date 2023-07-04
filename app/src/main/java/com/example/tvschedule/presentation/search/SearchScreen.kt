package com.example.tvschedule.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tvschedule.presentation.search.model.SearchNavCallback
import com.example.tvschedule.presentation.search.model.SearchUiEvent
import com.example.tvschedule.presentation.search.model.SearchUiState
import com.example.tvschedule.presentation.ui.components.ErrorState
import com.example.tvschedule.presentation.ui.components.SearchEmptyState
import com.example.tvschedule.presentation.ui.components.SearchShowBar
import com.example.tvschedule.presentation.ui.components.ShowsList
import kotlinx.coroutines.launch


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigation: (SearchNavCallback) -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    SearchContent(
        state = state,
        onEvent = { viewModel.setEvent(it) },
        onShowClick = { navigation.invoke(SearchNavCallback.ShowDetails(it)) }
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun SearchContent(
    state: SearchUiState,
    onEvent: (SearchUiEvent) -> Unit,
    onShowClick: (showId: Long) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            SearchShowBar(
                query = state.searchQuery,
                onQueryChange = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                    onEvent.invoke(SearchUiEvent.OnQueryChange(it))
                }
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.isLoading,
            onRefresh = { onEvent.invoke(SearchUiEvent.Retry) }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
        ) {
            when {
                state.isError -> {
                    keyboardController?.hide()
                    ErrorState { onEvent.invoke(SearchUiEvent.Retry) }
                }

                state.shows.isNotEmpty() -> {
                    ShowsList(
                        shows = state.shows,
                        listState = listState,
                        onFavouriteClick = { showId: Long, isFavorite: Boolean ->
                            onEvent.invoke(SearchUiEvent.OnFavoriteClick(showId, isFavorite))
                        },
                        onItemClick = {
                            keyboardController?.hide()
                            onShowClick.invoke(it)
                        }
                    )
                }

                state.isLoading.not() -> {
                    SearchEmptyState(state.searchQuery.isNotEmpty())
                }
            }

            PullRefreshIndicator(
                refreshing = state.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
