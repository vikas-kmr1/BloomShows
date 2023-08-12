package com.android.bloomshows.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.bloomshows.R
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.ui.theme.ExtraSmallPadding
import com.android.bloomshows.ui.theme.MediumIcon
import com.android.bloomshows.ui.theme.SemiLargeTextSize
import com.android.bloomshows.ui.theme.handlee

@Composable
fun BloomshowsBranding(modifier: Modifier = Modifier) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.padding(horizontal = ExtraSmallPadding).size(MediumIcon),
                painter = painterResource(R.drawable.ic_bloomshows),
                contentDescription = stringResource(R.string.bloom_shows_logo)
            )
            Text(
                text = stringResource(R.string.app_name),
                style = TextStyle(
                    fontSize = SemiLargeTextSize,
                    fontWeight = FontWeight.Black,
                    fontFamily = handlee
                )
            )
        }
}

@Composable
fun PreviewBloomshowsBranding() {
    BloomShowsTheme {
        BloomshowsBranding()
    }
}