package com.android.bloomshows.utils.animations

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.bloomshows.R
import com.squareup.moshi.Json

@Composable
fun ShowLootieAnimation(
    modifier: Modifier = Modifier,
    animationJsonResId: Int,
    onComplete: () -> Unit = {},
    speed: Float = 1f,
    repeat: Boolean = false,
    scale: ContentScale = ContentScale.Fit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationJsonResId))
    val logoAnimationState = animateLottieCompositionAsState(
        composition = composition,
        speed = speed,
        iterations = if (repeat) LottieConstants.IterateForever else 1
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { logoAnimationState.progress },
        contentScale = scale
    )
    if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
        onComplete()
    }
}