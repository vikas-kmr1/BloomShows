package com.android.bloomshows.ui.common_components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.bloomshows.R

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
        placeholder = placeholder,
        onError = { },
        contentDescription = "$data image",
        contentScale = contentScale
    )
}