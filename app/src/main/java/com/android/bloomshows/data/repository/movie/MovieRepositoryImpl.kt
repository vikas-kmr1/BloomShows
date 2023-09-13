package com.android.bloomshows.data.repository.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.bloomshows.data.room.AppDatabase
import com.android.bloomshows.data.room.entity.MovieEntity
import com.android.bloomshows.network.services.the_movie_db.MovieApiService
import com.android.bloomshows.utils.MediaCategories
import com.android.bloomshows.utils.Time_Window
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val appDatabase: AppDatabase
) : MovieRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchMovieList(
        language: String,
        timeWindow: Time_Window,
        pageSize: Int,
        category: MediaCategories
    ): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(pageSize = pageSize),
        remoteMediator = MovieRemoteMediator(
            appDatabase = appDatabase,
            movieApiService = movieApiService,
            language = language,
            timeWindow = timeWindow,
            category = category
        )
    ) {
        appDatabase.theMovieDao().getMovieList(category.title)
    }.flow

    override fun fetchMovieById(id: String): MovieEntity =
        appDatabase.theMovieDao().getMovieById(id = id)
}