package com.android.bloomshows.presentation.on_boarding

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.bloomshows.R
import com.android.bloomshows.ui.components.ButtonWithIndicator
import com.android.bloomshows.ui.components.WormPageIndicator
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.ui.theme.MediumPadding


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun OnBoardingScreen(
    slides: List<DataOnBoarding> = onBoardingSlides, navigate_to_login: () -> Unit = {}
) {
    //TODO add reveal and text trasition
    /*  // Not Using for now :
        //screen orientation check
        var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
        val configuration = LocalConfiguration.current

        LaunchedEffect(configuration) {
            // Save any changes to the orientation value on the configuration object
            snapshotFlow { configuration.orientation }
                .collect { orientation = it }
        }

        val offetVertical = when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 70.dp
            else -> {
                -50.dp
            }
        }*/
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
                    .background(slides[pagerState.currentPage].backgroundColor),
                contentAlignment = Alignment.Center
            ) {}
        }

        TopContent(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter).offset(y = 30.dp)
                .windowInsetsPadding(WindowInsets.statusBars),
            navigate_to_login = { navigate_to_login() },
            pagerState.currentPage
        )

        Column(
            modifier = Modifier.fillMaxWidth().offset(y = 60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                modifier = Modifier,
                contentScale = ContentScale.Fit,
                painter = painterResource(slides[pagerState.currentPage].illustration),
                contentDescription = "slide ${pagerState.currentPage} illustrations"
            )
            LabelGroup(
                slides[pagerState.currentPage],
                Modifier
            )
        }
        Box(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(MediumPadding)) {
            WormPageIndicator(
                modifier = Modifier.align(Alignment.BottomCenter),
                totalPages = pageCount,
                currentPage = pagerState.currentPage
            )
            ButtonWithIndicator(
                modifier = Modifier.align(Alignment.BottomEnd),
                pagerState = pagerState,
                nav_to_login = navigate_to_login
            )
        }
    }
}

@Composable
fun TopContent(modifier: Modifier = Modifier, navigate_to_login: () -> Unit, pageInd: Int) {
    Row(
        modifier = modifier.padding(horizontal = MediumPadding),
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
    Column(modifier = modifier.padding(horizontal = MediumPadding)) {
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