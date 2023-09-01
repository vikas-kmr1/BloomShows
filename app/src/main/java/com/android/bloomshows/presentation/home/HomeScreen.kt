package com.android.bloomshows.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.bloomshows.R
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.utils.animations.ShowLootieAnimation

@Composable
fun HomeScreen(
    onLogOut: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val firstTime by homeViewModel.isFirstTime.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (firstTime) {
            ShowLootieAnimation(
                modifier = Modifier.fillMaxSize().scale(2.0f).zIndex(100f).background(Color.Transparent),
                animationJsonResId = R.raw.animation_sparkling,
                onComplete = {
                    // call only when user launched for the very first time
                    homeViewModel.saveUserFirstTime()
                },
            )
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