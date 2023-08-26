package com.android.bloomshows.ui.common_components

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.bloomshows.ui.theme.BloomShowsTheme
import kotlinx.coroutines.launch


@Composable
fun WormPageIndicator(
    totalPages: Int = 1,
    currentPage: Int = 0,
    modifier: Modifier = Modifier,
    indicatorSize: Dp = 8.dp,
    color: Color = Color.White,
    spacing: Dp = indicatorSize,
    selectedMultiplier: Int = 3,
    vibrateOnSwipe: Boolean = true
) {

    assert(value = currentPage in 0 until totalPages,
        lazyMessage = { "Current page index is out of range." })

    val vibrator = LocalContext.current.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val coroutineScope = rememberCoroutineScope()

    val rowWidth =
        (indicatorSize * (selectedMultiplier + (totalPages - 1))) + (spacing * (totalPages - 1))
    Row(
        modifier = modifier.requiredWidth(rowWidth).animateContentSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0 until totalPages) {
            val selected = i == currentPage

            val animSpec = remember {
                tween<Dp>(
                    durationMillis = DURATION_MILLIS,
                    easing = LinearEasing,
                    delayMillis = 0
                )
            }

            val height = indicatorSize
            val width: Dp by animateDpAsState(
                if (selected) indicatorSize * selectedMultiplier else indicatorSize,
                animationSpec = animSpec
            )

            Canvas(modifier = Modifier.size(width, height), onDraw = {
                drawRoundRect(
                    color = color,
                    cornerRadius = CornerRadius(height.toPx() / 2),
                    size = Size(width.toPx(), height.toPx())
                )
            })
        }
    }

    if (vibrateOnSwipe) {
        LaunchedEffect(currentPage) {
            coroutineScope.launch {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        60,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }
        }
    }
}

private const val INDICATOR_FADE_IN_ANIMATION_DELAY = 200
private const val DURATION_MILLIS = 150

@Preview
@Composable
fun PreviewWormPageIndicator(){
    BloomShowsTheme {
        WormPageIndicator(
            totalPages = 3,
            currentPage = 1
        )
    }
}


