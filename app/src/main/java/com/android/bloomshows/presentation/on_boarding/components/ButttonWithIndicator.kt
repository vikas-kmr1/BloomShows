package com.android.bloomshows.presentation.on_boarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.ui.theme.MediumPadding
import com.android.bloomshows.ui.theme.onBoardCyan
import com.android.bloomshows.ui.theme.onBoardPink
import com.android.bloomshows.ui.theme.onBoardYellow
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ButtonWithIndicator(
    modifier: Modifier,
    pagerState: PagerState,
    color: Color = Color(0xFFFAF4F4),
    nav_to_login: () -> Unit = {}
) {
    val pageInd = pagerState.currentPage
    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier.size(70.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(color),

        ) {

        //left top corner
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(width = 35.dp, height = 42.dp)
                .padding(all = 3.dp)
                .clip(RoundedCornerShape(topStart = 22.dp))
                .background(
                    if (pageInd >= 0) onBoardYellow else Color.Gray
                )
        ) {}

        //right top corner

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(width = 35.dp, height = 42.dp)
                .padding(all = 3.dp)
                .clip(RoundedCornerShape(topEnd = 22.dp))
                .background(
                    if (pageInd >= 1) onBoardCyan else Color.LightGray
                )
        ) {}

        // bottom Curve
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(width = 70.dp, height = 28.dp)
                .padding(all = 3.dp)
                .clip(RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp))
                .background(
                    if (pageInd == 2) onBoardPink else Color.LightGray
                )
        ) {}


        IconButton(
            onClick = {
                if (pageInd < 2)
                    scope.launch { pagerState.animateScrollToPage(pageInd + 1) }
                else nav_to_login()
            },
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.Center)

                .padding(6.dp)
                .clip(RoundedCornerShape(MediumPadding)),
            colors = IconButtonDefaults.iconButtonColors(containerColor = color),
            interactionSource = MutableInteractionSource()
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                tint = Color.Black,
                contentDescription = null
            )
        }
    }
}


// curves for button corners
//TOOD make curve more symetric
/*
 not usin grigh now
*/

/*
@Composable
fun ButtonCurve(modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {

        val width = size.width
        val height = size.height
        val halfWidth = width.times(.5f)
        val halfHeight = height.times(1.3f)

        val startPoints = listOf(
            PointF(0f, 0f),
        )

        val path = Path().apply {
            startPoints.forEach { point ->

                if (point.x > halfWidth)
                    width.minus(halfWidth.times(.3f))
                else
                    halfWidth.times(.3f)

                val curveXPart1 =
                    if (point.x > halfWidth)
                        width.minus(halfWidth.times(.5f))
                    else
                        halfWidth.times(.5f)

                if (point.x > halfWidth)
                    width.minus(halfWidth.times(.7f))
                else
                    halfWidth.times(.7f)

                val curve1 = PointF(curveXPart1, point.y)
                val curve2 = PointF(curveXPart1, halfHeight - ((halfHeight - point.y) / 2))

                moveTo(point.x, point.y)
                quadraticBezierTo(
                    curve1.x,
                    curve1.y,
                    curve2.x,
                    curve2.y,
                )
            }
        }
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(width = 20f, cap = StrokeCap.Round)
        )
    }
}
*/


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun PreviewButtonWithIndicator() {
    BloomShowsTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ButtonWithIndicator(modifier = Modifier, pagerState =  rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f
            ) {
                3
            })
        }
    }
}