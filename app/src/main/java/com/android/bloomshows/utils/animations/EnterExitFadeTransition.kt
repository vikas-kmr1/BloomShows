package com.android.bloomshows.utils.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnterExitFadeTransition(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(
            animationSpec = tween(
                300, easing = LinearEasing
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                300, easing = LinearEasing
            )
        ),
        content = content,
        initiallyVisible = false
    )
}

