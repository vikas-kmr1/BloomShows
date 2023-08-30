package com.android.bloomshows.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.bloomshows.R
import com.android.bloomshows.presentation.login_and_signup.login.LoginViewModel
import com.android.bloomshows.ui.theme.BloomShowsTheme

@Composable
fun HomeScreen(
    onLogOut: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val firstTime by homeViewModel.isFirstTime.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
        if (firstTime) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_sparkling))
            val logoAnimationState =
                animateLottieCompositionAsState(composition = composition, speed = 1f)
            LottieAnimation(
                modifier = Modifier.scale(2.5f).zIndex(100f).background(Color.Transparent),
                composition = composition,
                progress = { logoAnimationState.progress }
            )
            if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
                //TODO call only when user laucnhed for the very first time
                homeViewModel.saveUserFirstTime()
            }
        }
        Button(onClick = onLogOut) {
            Text(text = "LogOut")
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    BloomShowsTheme {
        HomeScreen(

            {}
        )
    }
}