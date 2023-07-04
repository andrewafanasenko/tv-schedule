package com.example.tvschedule.presentation.show_details

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tvschedule.presentation.show_details.model.ShowDetailsNavCallback


@Composable
fun ShowDetailsScreen(
    viewModel: ShowDetailsViewModel = hiltViewModel(),
    navigation: (ShowDetailsNavCallback) -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    BackHandler { navigation.invoke(ShowDetailsNavCallback.Back) }
}
