package com.android.bloomshows.ui.common_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CoilImage(data:Any?, modifier: Modifier = Modifier,
              placeholder: Painter?,
              contentScale: ContentScale) {
    AsyncImage(
        model = ImageRequest
            .Builder(context = LocalContext.current)
            .data(data)
            .crossfade(true)
            .build(),
        modifier = modifier,
        onError = { },
        contentDescription = "$data image",
        contentScale = contentScale,
        placeholder = placeholder
    )
}