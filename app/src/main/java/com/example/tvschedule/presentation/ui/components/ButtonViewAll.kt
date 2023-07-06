package com.example.tvschedule.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tvschedule.R


@Composable
fun ButtonViewAll(onClick: () -> Unit) {
    FilledTonalButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = R.string.view_all),
            style = MaterialTheme.typography.labelLarge
        )
    }
}
