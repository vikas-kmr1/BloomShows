package com.android.bloomshows.presentation.on_boarding

import CircleRevealPager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.android.bloomshows.R
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.ui.theme.MediumPadding


@Composable
fun OnBoardingScreen(
    slides: List<DataOnBoarding> = onBoardingSlides, navigate_to_login: () -> Unit = {}
) {
    //TODO add  text trasition and aninmation
    
    /*  // Not Using for now :
        //screen orientation check
        var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
        val configuration = LocalConfiguration.current*/

    CircleRevealPager(slides = slides, navigate_to_login = navigate_to_login)
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

        TextButton(onClick = navigate_to_login, enabled = pageInd < 2, modifier = Modifier.zIndex(100f)) {
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

    Column(
        modifier = modifier.padding(horizontal = MediumPadding)
    ) {

        Text(
            text = page.label,
            color = Color.Black,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
        )
        Text(
            text = page.subLabel,
            color = Color.Black,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
        )
    }
}
@Preview
@Composable
fun PreviewOnBoardingScreen() {
    BloomShowsTheme {
        OnBoardingScreen()
    }
}