package com.android.bloomshows.data.repository.movie

import androidx.paging.PagingData
import com.android.bloomshows.data.room.entity.MovieEntity
import com.android.bloomshows.utils.MediaCategories
import com.android.bloomshows.utils.Time_Window
import kotlinx.coroutines.flow.Flow


interface MovieRepository {

    fun fetchMovieList(
        language: String,
        timeWindow: Time_Window,
        pageSize: Int,
        category: MediaCategories
    ): Flow<PagingData<MovieEntity>>

    fun fetchMovieById(
        id: String
    ): MovieEntity
}