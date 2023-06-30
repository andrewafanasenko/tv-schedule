package com.example.tvschedule.presentation.show_search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tvschedule.R
import com.example.tvschedule.presentation.show_search.model.SearchUiEvent
import com.example.tvschedule.presentation.show_search.model.SearchUiState
import com.example.tvschedule.presentation.show_search.model.ShowItem


@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    SearchContent(
        state = state,
        onEvent = { viewModel.setEvent(it) }
    )
}

@Composable
private fun SearchContent(
    state: SearchUiState,
    onEvent: (SearchUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SearchShowBar(
                query = state.searchQuery,
                onQueryChange = { onEvent.invoke(SearchUiEvent.OnQueryChange(it)) }
            )
        }
    ) { paddingValues ->
        Shows(paddingValues = paddingValues, shows = state.shows)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchShowBar(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifier),
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(stringResource(id = R.string.search_hint)) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { onQueryChange.invoke("") },
                    content = { Icon(Icons.Filled.Close, contentDescription = null) }
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(28.dp),

        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        )
    )
}

@Composable
private fun Shows(paddingValues: PaddingValues, shows: List<ShowItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = shows,
            key = { it.id }
        ) { item ->
            Text(item.name)
        }
    }
}
