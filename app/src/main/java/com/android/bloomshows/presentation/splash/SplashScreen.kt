package com.android.bloomshows.presentation.splash

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.bloomshows.R
import com.android.bloomshows.ui.components.BloomshowsBranding
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.ui.theme.MediumPadding

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SplashScreen(
    navigate_to_home: () -> Unit,
    navigate_to_onboarding: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SplashLogoBloomShows(navigate_to_home, navigate_to_onboarding)
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center).offset(y = 100.dp),
            color = Color.Gray.copy(alpha = 0.5f)
        )

        BloomshowsBranding(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = MediumPadding),
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun SplashLogoBloomShows(
    navigate_to_home: () -> Unit,
    navigate_to_onboarding: () -> Unit,
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animated_logo_bloomshows))
    val logoAnimationState =
        animateLottieCompositionAsState(composition = composition, speed = 2.5f)
    LottieAnimation(
        modifier = Modifier.size(108.dp),
        composition = composition,
        progress = { logoAnimationState.progress }
    )
    if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
        //TODO call only when user laucnhed for the very first time
        navigate_to_onboarding()

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SplashScreenPreview() {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        BloomShowsTheme(darkTheme = false) {
            SplashScreen(navigate_to_home = {}, navigate_to_onboarding = {})
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SplashScreenPreviewDark() {

    BloomShowsTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SplashScreen(navigate_to_home = {}, navigate_to_onboarding = {})
        }
    }
}