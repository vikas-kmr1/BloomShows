package com.android.bloomshows.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.bloomshows.R
import com.android.bloomshows.ui.theme.BloomShowsTheme

@Composable
fun HomeScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_sparkling))
    val logoAnimationState =
        animateLottieCompositionAsState(composition = composition, speed = 1f)
    LottieAnimation(
        modifier = Modifier.fillMaxSize().background(Color.LightGray),
        composition = composition,
        progress = { logoAnimationState.progress }
    )
    if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
        //TODO call only when user laucnhed for the very first time

    }
}

@Preview
@Composable
fun PreviewHomeScreen(){
    BloomShowsTheme {
        HomeScreen()
    }
}