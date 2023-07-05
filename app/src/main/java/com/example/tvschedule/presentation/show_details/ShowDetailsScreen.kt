package com.example.tvschedule.presentation.show_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tvschedule.presentation.show_details.model.ShowDetailsNavCallback
import com.example.tvschedule.presentation.show_details.model.ShowDetailsUiEvent
import com.example.tvschedule.presentation.show_details.model.ShowDetailsUiState
import com.example.tvschedule.presentation.ui.components.ImageCard
import com.example.tvschedule.presentation.ui.components.ImageWithPlaceholder


@Composable
fun ShowDetailsScreen(
    viewModel: ShowDetailsViewModel = hiltViewModel(),
    navigation: (ShowDetailsNavCallback) -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    BackHandler { navigation.invoke(ShowDetailsNavCallback.Back) }
    ShowDetailsContent(
        state = state,
        onEvent = { viewModel.setEvent(it) },
        navigation = navigation
    )
}

@Composable
private fun ShowDetailsContent(
    state: ShowDetailsUiState,
    onEvent: (ShowDetailsUiEvent) -> Unit,
    navigation: (ShowDetailsNavCallback) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        ShowDetailsList(state)
        TopBar {
            navigation.invoke(ShowDetailsNavCallback.Back)
        }
    }
}

@Composable
private fun ShowDetailsList(state: ShowDetailsUiState) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { Cover(state.coverUrl) }
        item { ShowName(state.showName) }
    }
}

@Composable
private fun Cover(coverUrl: String) {
    Box(contentAlignment = Alignment.TopCenter) {
        val backgroundColor = MaterialTheme.colorScheme.background
        ImageWithPlaceholder(
            imageUrl = coverUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .blur(300.dp)
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            Brush.verticalGradient(
                                0f to Color.Transparent,
                                0.2f to backgroundColor,
                                1F to backgroundColor
                            )
                        )
                    }
                },
            contentScale = ContentScale.Crop
        )
        if (coverUrl.isBlank()) {
            ImageCard(
                coverUrl = coverUrl,
                modifier = Modifier
                    .height(360.dp)
                    .width(200.dp)
                    .padding(top = 56.dp),
                elevation = 4.dp
            )
        } else {
            ImageWithPlaceholder(
                imageUrl = coverUrl,
                modifier = Modifier
                    .height(380.dp)
                    .widthIn(min = 160.dp)
                    .padding(top = 56.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun ShowName(name: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = name,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun TopBar(onNavigationIconClick: () -> Unit) {
    TopAppBar(
        title = { /* Ignored */ },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = Modifier.height(56.dp),
        navigationIcon = {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                ),
                onClick = onNavigationIconClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}
