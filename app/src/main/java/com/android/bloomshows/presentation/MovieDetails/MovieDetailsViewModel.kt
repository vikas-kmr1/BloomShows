package com.android.bloomshows.presentation.MovieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bloomshows.data.repository.movie.MovieRepository
import com.android.bloomshows.data.room.entity.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) :ViewModel(){

    var movie: MovieEntity? = null


    fun getMovieById(id:String) {
        viewModelScope.launch(Dispatchers.IO) {
            movie = movieRepository.fetchMovieById(id)
        }
        Timber.tag("movie").v(movie.toString())
    }
}