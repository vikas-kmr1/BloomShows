package com.android.bloomshows.presentation.on_boarding

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.bloomshows.R
import com.android.bloomshows.ui.compoents.ButtonWithIndicator
import com.android.bloomshows.ui.compoents.WormPageIndicator
import com.android.bloomshows.ui.theme.BloomShowsTheme
import kotlin.text.Typography.times


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun OnBoardingScreen(
    slides: List<DataOnBoarding> = onBoardingSlides, navigate_to_login: () -> Unit = {}
) {
    val pagerState = rememberPagerState(0)

    val pageCount = slides.size
    Box() {
        HorizontalPager(
            verticalAlignment = Alignment.CenterVertically,
            pageCount = pageCount,
            state = pagerState
        ) { page ->
            // Our page content
            //TODO add drawables islustrations

            Box(
                modifier = Modifier.fillMaxSize()

                    .background(slides[pagerState.currentPage].backgroundColor)

            )
            {
            }
        }

        TopContent(
            modifier = Modifier.fillMaxWidth().offset(y = 50.dp).align(Alignment.TopCenter),
            navigate_to_login = { navigate_to_login() },
            pagerState.currentPage
        )

        LabelGroup(slides[pagerState.currentPage], Modifier.align(Alignment.CenterStart))
        WormPageIndicator(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
            totalPages = pageCount,
            currentPage = pagerState.currentPage
        )
        ButtonWithIndicator(
            modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 30.dp, end = 20.dp),
            pagerState = pagerState,
            nav_to_login = navigate_to_login
        )
    }
}

@Composable
fun TopContent(modifier: Modifier = Modifier, navigate_to_login: () -> Unit, pageInd: Int) {
    Row(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(
            modifier = Modifier.size(36.dp),
            painter = painterResource(R.drawable.ic_launcher_foreground),
            tint = Color.Black,
            contentDescription = "Logo"
        )

        TextButton(onClick = navigate_to_login, enabled = pageInd < 2) {
            Text(
                text = if (pageInd < 2) stringResource(R.string.skip) else "",
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall
            )
        }

    }

}

@Composable
fun LabelGroup(page: DataOnBoarding, modifier: Modifier = Modifier) {
    Column(modifier = modifier.offset(y = 60.dp).padding(horizontal = 20.dp)) {
        Text(text = page.label, color = Color.Black, style = MaterialTheme.typography.labelLarge)
        Text(text = page.subLabel, color = Color.Black, style = MaterialTheme.typography.labelSmall)
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewOnBoardingScreen() {
    BloomShowsTheme {
        OnBoardingScreen()
    }
}