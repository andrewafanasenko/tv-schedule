package com.example.tvschedule.presentation.show_details

import android.text.Html
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tvschedule.R
import com.example.tvschedule.presentation.show_details.model.CastItem
import com.example.tvschedule.presentation.show_details.model.SeasonItem
import com.example.tvschedule.presentation.show_details.model.ShowDetailsNavCallback
import com.example.tvschedule.presentation.show_details.model.ShowDetailsUiEvent
import com.example.tvschedule.presentation.show_details.model.ShowDetailsUiState
import com.example.tvschedule.presentation.ui.components.ButtonViewAll
import com.example.tvschedule.presentation.ui.components.ExpandableText
import com.example.tvschedule.presentation.ui.components.FavouriteButton
import com.example.tvschedule.presentation.ui.components.ImageCard
import com.example.tvschedule.presentation.ui.components.ImageWithPlaceholder
import com.example.tvschedule.presentation.ui.components.ItemCast
import com.example.tvschedule.presentation.ui.components.ItemSeason
import com.example.tvschedule.presentation.ui.components.RatingPill
import kotlinx.collections.immutable.ImmutableList


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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                onClick = {
                    //Ignored
                }
            ) {
                FavouriteButton(
                    isFavourite = state.isFavorite,
                    onFavouriteClick = {
                        onEvent.invoke(ShowDetailsUiEvent.OnFavoriteClick)
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ShowDetailsList(state, onEvent)
            TopBar {
                navigation.invoke(ShowDetailsNavCallback.Back)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowDetailsList(
    state: ShowDetailsUiState,
    onEvent: (ShowDetailsUiEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item { Cover(state.coverUrl, state.rating) }
        stickyHeader { Name(state.showName) }
        item { Genres(state.genres) }
        item { Summary(state.summary) }
        cast(state.cast, state.isViewAllCastButtonVisible) {
            onEvent.invoke(ShowDetailsUiEvent.OnShowAllCastClick)
        }
        seasons(state.seasons, state.isViewAllSeasonsButtonVisible) {
            onEvent.invoke(ShowDetailsUiEvent.OnShowAllSeasonsClick)
        }
    }
}

@Composable
private fun Cover(coverUrl: String, rating: Double?) {
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
            rating?.let {
                RatingPill(
                    rating = it,
                    modifier = Modifier
                        .padding(top = 384.dp)
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
                        .align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Composable
private fun Name(name: String) {
    Text(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(horizontal = 56.dp, vertical = 14.dp),
        text = name,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Genres(genres: ImmutableList<String>) {
    if (genres.isEmpty()) return
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        genres.forEach { genre ->
            ElevatedAssistChip(
                modifier = Modifier.padding(4.dp),
                onClick = { /* Ignored */ },
                shape = RoundedCornerShape(16.dp),
                label = {
                    Text(
                        text = genre,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            )
        }
    }
}

@Composable
private fun Summary(summary: String) {
    ExpandableText(
        text = Html.fromHtml(summary, Html.FROM_HTML_MODE_LEGACY).toString(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
    )
}

private fun LazyListScope.cast(
    cast: ImmutableList<CastItem>,
    isViewAllVisible: Boolean,
    onViewAllClick: () -> Unit
) {
    if (cast.isEmpty()) return
    item { SectionHeader(stringResource(id = R.string.cast_title)) }
    items(cast) { ItemCast(it) }
    if (isViewAllVisible) {
        item { ButtonViewAll(onViewAllClick) }
    }
}

private fun LazyListScope.seasons(
    seasons: ImmutableList<SeasonItem>,
    isViewAllVisible: Boolean,
    onViewAllClick: () -> Unit
) {
    if (seasons.isEmpty()) return
    item { SectionHeader(stringResource(id = R.string.seasons_title)) }
    items(seasons) { ItemSeason(it) }
    if (isViewAllVisible) {
        item { ButtonViewAll(onViewAllClick) }
    }
}

@Composable
private fun SectionHeader(name: String) {
    Text(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 8.dp
        ),
        text = name,
        style = MaterialTheme.typography.titleMedium
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
