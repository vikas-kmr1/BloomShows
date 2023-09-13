package com.android.bloomshows.presentation.home.components

import android.widget.Button
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.bloomshows.R
import com.android.bloomshows.ui.common_components.AnimatedCircularBarWithText
import com.android.bloomshows.ui.common_components.CoilImage
import com.android.bloomshows.ui.theme.MediumPadding
import java.time.format.TextStyle


@Composable
fun MovieItem(
    posterPath: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ElevatedCard(
        modifier = modifier.clip(RoundedCornerShape(MediumPadding)).clickable(onClick = onClick)
    ) {
        CoilImage(
            modifier = Modifier.sizeIn(
                minHeight = 220.dp,
                minWidth = 140.dp,
            ),
            placeholder = painterResource(R.drawable.loading_img),
            data = posterPath,
            contentScale = ContentScale.FillBounds
        )
    }
}

