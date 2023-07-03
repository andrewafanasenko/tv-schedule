package com.example.tvschedule.presentation.favorite

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
import com.example.tvschedule.presentation.favorite.model.FavoriteUiEvent
import com.example.tvschedule.presentation.favorite.model.FavoriteUiState
import com.example.tvschedule.presentation.ui.components.EmptyState
import com.example.tvschedule.presentation.ui.components.ErrorState
import com.example.tvschedule.presentation.ui.components.SearchEmptyState
import com.example.tvschedule.presentation.ui.components.SearchShowBar
import com.example.tvschedule.presentation.ui.components.ShowsList
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    FavoriteContent(
        state = state,
        onEvent = { viewModel.setEvent(it) }
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
private fun FavoriteContent(
    state: FavoriteUiState,
    onEvent: (FavoriteUiEvent) -> Unit
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
                    onEvent.invoke(FavoriteUiEvent.OnQueryChange(it))
                }
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.isLoading,
            onRefresh = { onEvent.invoke(FavoriteUiEvent.Retry) }
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
                    ErrorState { onEvent.invoke(FavoriteUiEvent.Retry) }
                }

                state.shows.isNotEmpty() -> {
                    ShowsList(
                        shows = state.shows,
                        listState = listState,
                        onFavouriteClick = { showId: Long, _ ->
                            onEvent.invoke(FavoriteUiEvent.OnFavoriteClick(showId))
                        }
                    )
                }

                state.isLoading.not() -> {
                    if (state.searchQuery.isEmpty()) {
                        EmptyState()
                    } else {
                        SearchEmptyState(state.searchQuery.isNotEmpty())
                    }
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
