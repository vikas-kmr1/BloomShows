package com.android.bloomshows.ui.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.bloomshows.R
import com.android.bloomshows.utils.animations.ShowLootieAnimation
import com.android.bloomshows.utils.animations.shimmerBrush

@Composable
fun CoilImage(
    data: Any?, modifier: Modifier = Modifier,
    placeholder: Painter?,
    contentScale: ContentScale
) {

    val showShimmer = remember { mutableStateOf(true) }

    AsyncImage(
        model = ImageRequest
            .Builder(context = LocalContext.current)
            .data(data)
            .crossfade(false)
            .build(),
        modifier = modifier.background(
            shimmerBrush(
                targetValue = 1300f,
                showShimmer = showShimmer.value
            )
        ),
        onSuccess = { showShimmer.value = false },
        contentScale = ContentScale.Crop,
        contentDescription = "$data image",
        )


}