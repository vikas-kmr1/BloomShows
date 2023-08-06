package com.android.bloomshows.presentation.on_boarding

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.bloomshows.ui.compoents.WormPageIndicator
import com.android.bloomshows.ui.theme.BloomShowsTheme


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(slides: List<DataOnBoarding> = onBoardingSlides) {
    val pagerState = rememberPagerState(0)
    val pageCount = slides.size
    Box() {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            pageCount = pageCount,
            state = pagerState
        ) { page ->
            // Our page content
            OnBoardingSlide(slides[page])
        }
        WormPageIndicator(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 30.dp),
            totalPages = pageCount,
            currentPage = pagerState.currentPage
        )
    }
}


@Composable()
fun OnBoardingSlide(slide: DataOnBoarding) {
    Column(modifier = Modifier.fillMaxSize().background(slide.backgroundColor)) {
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