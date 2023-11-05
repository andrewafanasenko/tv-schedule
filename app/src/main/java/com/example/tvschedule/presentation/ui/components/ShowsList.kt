package com.example.tvschedule.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.tvschedule.presentation.search.model.ShowItem
import kotlinx.collections.immutable.ImmutableList


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun ShowsList(
    shows: ImmutableList<ShowItem>,
    listState: LazyListState,
    onFavouriteClick: (id: Long, isFavorite: Boolean) -> Unit,
    onItemClick: (showId: Long) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { keyboardController?.hide() }
                )
            },
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = shows,
            key = { it.id }
        ) { item ->
            ItemShow(
                show = item,
                onFavouriteClick = onFavouriteClick,
                modifier = Modifier
                    .animateItemPlacement()
                    .clickable { onItemClick.invoke(item.id) }
            )
        }
    }
}
