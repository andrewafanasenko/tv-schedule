package com.example.tvschedule.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.tvschedule.R


@Composable
fun ImageCard(coverUrl: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 16.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(coverUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Image(
                    modifier = Modifier.padding(16.dp),
                    painter = painterResource(id = R.drawable.image_placeholder),
                    contentDescription = null
                )
            } else {
                SubcomposeAsyncImageContent()
            }
        }
    }
}
