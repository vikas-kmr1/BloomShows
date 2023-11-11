package com.android.bloomshows.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.bloomshows.R
import com.android.bloomshows.ui.common_components.CoilImage
import com.android.bloomshows.ui.theme.MediumPadding


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
            modifier = modifier,
            placeholder = painterResource(R.drawable.loading_img),
            data = posterPath,
            contentScale = ContentScale.FillBounds
        )
    }
}

