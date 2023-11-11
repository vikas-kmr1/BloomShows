package com.android.bloomshows.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.bloomshows.data.room.entity.MovieEntity
import com.android.bloomshows.presentation.home.HomeViewModel
import com.android.bloomshows.ui.common_components.MovieTopAppBar
import com.android.bloomshows.utils.MediaCategories
import com.android.bloomshows.utils.extension_fun.items

@Composable
fun MoviesVerticalGridList(
    modifier: Modifier = Modifier,
    category: String,
    navigateToDetails: (id: String, name: String) -> Unit,
    navigateToHome: () -> Unit,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val moviesList: LazyPagingItems<MovieEntity>? =
        when (category) {
            MediaCategories.TRENDING.title -> homeViewModel.trendingPagingFlow.collectAsLazyPagingItems()
            MediaCategories.POPULAR.title -> homeViewModel.popularPagingFlow.collectAsLazyPagingItems()
            MediaCategories.NOW_PLAYING.title -> homeViewModel.nowPlayingPagingFlow.collectAsLazyPagingItems()
            else -> null
        }
    Column(modifier = modifier) {
        MovieTopAppBar(
            modifier = Modifier.zIndex(100f),
            title = category,
            handleNavBack = { navigateToHome() }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (moviesList != null)
                items(items = moviesList) { item ->
                    MovieItem(
                        posterPath = item?.posterPath,
                        modifier = Modifier.sizeIn(
                            minHeight = 160.dp,
                            minWidth = 140.dp,
                        )
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