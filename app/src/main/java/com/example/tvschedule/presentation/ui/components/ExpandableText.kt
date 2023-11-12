package com.example.tvschedule.presentation.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tvschedule.R


@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier,
    maxLines: Int = 3,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    textAlign: TextAlign = TextAlign.Center
) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val isExpandable by remember { derivedStateOf { textLayoutResult?.didOverflowHeight ?: false } }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val isButtonVisible by remember { derivedStateOf { isExpandable || isExpanded } }

    Column(
        modifier = modifier
    ) {
        Text(
            text = text.trim(),
            modifier = Modifier.animateContentSize(),
            style = style,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            overflow = TextOverflow.Ellipsis,
            textAlign = textAlign,
            onTextLayout = { textLayoutResult = it }
        )

        if (isButtonVisible) {
            TextButton(
                onClick = { isExpanded = isExpanded.not() },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = stringResource(
                        if (isExpanded) R.string.show_less else R.string.show_more
                    )
                )
            }
        }
    }
}
