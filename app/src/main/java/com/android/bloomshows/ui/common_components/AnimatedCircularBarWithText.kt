package com.android.bloomshows.ui.common_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun AnimatedCircularBarWithText(
    targetValue: Float = 60f,
    numberStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.labelSmall,
    size: Dp = 40.dp,
    thickness: Dp = 3.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    foregroundIndicatorColor: Color = Color(0xFF35898f),
    backgroundIndicatorColor: Color = foregroundIndicatorColor.copy(alpha = 0.5f),
    extraSizeForegroundIndicator: Dp = 1.dp
) {

    // It remembers the targerValues value
    var progress by remember {
        mutableStateOf(-1f)
    }

    // Number Animation
    val animateNumber = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        ), label = "average vote"
    )

    // This is to start the animation when the activity is opened
    LaunchedEffect(Unit) {
        progress = targetValue
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size = size)
    ) {
        Canvas(
            modifier = Modifier
                .size(size = size)
        ) {

            // Background circle
            drawCircle(
                color = backgroundIndicatorColor,
                radius = size.toPx() / 2,
                style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round)
            )

            val sweepAngle = (animateNumber.value / 100) * 360

            // Foreground circle
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke((thickness + extraSizeForegroundIndicator).toPx(), cap = StrokeCap.Butt)
            )
        }

        // Text that shows targerValues inside the circle
        Text(
            text = (animateNumber.value).toInt().toString(),
            style = numberStyle
        )
    }
}
