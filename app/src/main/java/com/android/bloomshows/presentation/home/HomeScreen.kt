package com.android.bloomshows.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.android.bloomshows.R
import com.android.bloomshows.data.room.entity.MovieEntity
import com.android.bloomshows.presentation.home.components.MovieItem
import com.android.bloomshows.presentation.home.components.MoviesVerticalGridList
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.ui.theme.ExtraSmallPadding
import com.android.bloomshows.ui.theme.SemiLargeTextSize
import com.android.bloomshows.ui.theme.SmallPadding
import com.android.bloomshows.ui.theme.handlee
import com.android.bloomshows.utils.MediaCategories
import com.android.bloomshows.utils.animations.ShowLootieAnimation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogOut: () -> Unit,
    homeViewModel: HomeViewModel,
    navigateToDetails: (id: String, name: String) -> Unit,
    navigateToMore: (MediaCategories) -> Unit
) {
    val firstTime by homeViewModel.isFirstTime.collectAsState()
    val trendingList = homeViewModel.trendingPagingFlow.collectAsLazyPagingItems()
    val popularList = homeViewModel.popularPagingFlow.collectAsLazyPagingItems()
    val nowPlayingList = homeViewModel.nowPlayingPagingFlow.collectAsLazyPagingItems()
    val context = LocalContext.current


    if (firstTime) {
        ShowLootieAnimation(
            modifier = Modifier
                .fillMaxSize()
                .scale(2.0f)
                .zIndex(100f)
                .background(Color.Transparent),
            animationJsonResId = R.raw.animation_sparkling,
            onComplete = {
                // call only when user launched for the very first time
                homeViewModel.saveUserFirstTime()
            },
        )
    }

    Scaffold(
        topBar = {
            Button(onClick = onLogOut, modifier = Modifier.wrapContentSize()) {
                Text(text = "LogOut")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(enabled = true, state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SmallPadding,Alignment.CenterVertically),
        ) {

            //Trending
            MoviesHorizontalList(
                moviesList = trendingList.itemSnapshotList,
                category = MediaCategories.TRENDING,
                navigateToDetails = navigateToDetails,
                navigateToMore = navigateToMore
            )
            //Now Playing
            MoviesHorizontalList(
                moviesList = nowPlayingList.itemSnapshotList,
                category = MediaCategories.NOW_PLAYING,
                navigateToDetails = navigateToDetails,
                navigateToMore = navigateToMore
            )
            //popular
            MoviesHorizontalList(
                moviesList = popularList.itemSnapshotList,
                category = MediaCategories.POPULAR,
                navigateToDetails = navigateToDetails,
                navigateToMore = navigateToMore
            )

        }
    }
}

@Composable
fun MoviesHorizontalList(
    moviesList: ItemSnapshotList<MovieEntity>,
    category: MediaCategories,
    navigateToDetails: (id: String, name: String) -> Unit,
    navigateToMore: (MediaCategories) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = category.title.capitalize(), style = TextStyle(
                    fontSize = SemiLargeTextSize,
                    fontWeight = FontWeight.Black,
                    fontFamily = handlee,
                )
            )
            IconButton(onClick = { navigateToMore(category) }) {
                Icon(imageVector = Icons.Filled.ArrowForwardIos, contentDescription = " ")
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding),
        ) {
            items(items = moviesList) { item ->
                MovieItem(
                    posterPath = item?.posterPath,
                    modifier = Modifier.aspectRatio(1/16f)
                ) {
                    navigateToDetails(
                        item?.id.toString(),
                        item?.title.toString()
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewHomeScreen() {
    BloomShowsTheme {
        HomeScreen(
            homeViewModel = hiltViewModel(),
            onLogOut = {},
            navigateToDetails = { _, _ -> },
            navigateToMore = {}
        )
    }
}