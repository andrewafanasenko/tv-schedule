package com.example.tvschedule.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tvschedule.R
import com.example.tvschedule.presentation.ui.keyboardState


@Composable
fun SearchEmptyState(isQueryExist: Boolean, modifier: Modifier = Modifier) {
    val isKeyboardVisible by keyboardState()
    val height = if (isKeyboardVisible) Modifier.height(200.dp) else Modifier.fillMaxHeight()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(height)
            .background(MaterialTheme.colorScheme.background)
            .clickable { /*Ignored*/ }
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.search_lottie)
        )
        LottieAnimation(
            modifier = Modifier
                .size(124.dp),
            iterations = LottieConstants.IterateForever,
            composition = composition
        )
        Text(
            text = stringResource(
                id = if (isQueryExist) {
                    R.string.no_results_description
                } else {
                    R.string.search_description
                }
            ),
            textAlign = TextAlign.Center
        )
    }
}
