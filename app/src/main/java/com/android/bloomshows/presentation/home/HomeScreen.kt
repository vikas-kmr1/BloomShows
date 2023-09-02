package com.android.bloomshows.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.ImageResult
import com.android.bloomshows.R
import com.android.bloomshows.ui.common_components.CoilImage
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.utils.animations.ShowLootieAnimation

@Composable
fun HomeScreen(
    onLogOut: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val firstTime by homeViewModel.isFirstTime.collectAsState()
    val curentUser by homeViewModel.currentUser.collectAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (firstTime) {
            ShowLootieAnimation(
                modifier = Modifier.fillMaxSize().scale(2.0f).zIndex(100f).background(Color.Transparent),
                animationJsonResId = R.raw.animation_sparkling,
                onComplete = {
                    // call only when user launched for the very first time
                    homeViewModel.saveUserFirstTime()
                },
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
            CoilImage(
                data = curentUser?.profilePic,
                modifier = Modifier.size(80.dp),
                placeholder = painterResource(R.drawable.ic_bloomshows),
                contentScale = ContentScale.Crop
            )
            Text(text = "name: "+curentUser?.username.toString())
            Text(text = "mail "+curentUser?.email.toString())
            Text(text = "uid: "+curentUser?.userId.toString())
            Text(text = "isanno: "+curentUser?.isAnonymous.toString())
            Text(text = "ver: "+curentUser?.emailVerfied.toString())
            Text(text = "creat: "+curentUser?.createdAT.toString())

            Button(onClick = onLogOut) {
                Text(text = "LogOut")
            }

        }


    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    BloomShowsTheme {
        HomeScreen(

            {}
        )
    }
}