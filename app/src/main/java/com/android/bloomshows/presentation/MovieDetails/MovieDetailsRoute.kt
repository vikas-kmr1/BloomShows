package com.android.bloomshows.presentation.MovieDetails

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MovieDetailsRoute(
    id:String,
    name:String,
    navigateUp: () -> Unit,
    movieDetailsViewModel:MovieDetailsViewModel = hiltViewModel()
){
    movieDetailsViewModel.getMovieById(id)
    movieDetailsViewModel.movie?.let {
        MovieDetailsScreen(
        navigateBack = navigateUp,
        movie = it
    )
    }
}