package com.android.bloomshows.network.services.the_movie_db

import com.android.bloomshows.network.model.movie.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApiService {
    /**
     * [now_playing](https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1)
     *[Movie Discover](https://developers.themoviedb.org/3/discover/movie-discover)
     **/
    @GET("/3/movie/popular?language=en-US")
    suspend fun fetchTv(@Query("page") page: Int): MovieResponse
    //ApiResponse<MovieResponse>


    /**
     * [trending-movies](https://api.themoviedb.org/3/trending/tv/day?language=en-US
     * [trending-movies](https://api.themoviedb.org/3/trending/tv/week?language=en-US
     */


    @GET("/3/trending/movie/{time_window}")
    suspend fun fetchTrendingTvs(
        @Path("time_window") timeWindow: String,
        @Query("language") language: String,
    ): MovieResponse

}