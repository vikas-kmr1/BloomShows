package com.android.bloomshows.network.services.the_movie_db

import com.android.bloomshows.network.model.movie.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    /**
     * [trending-movies](https://api.themoviedb.org/3/trending/movie/day?language=en-US
     * [trending-movies](https://api.themoviedb.org/3/trending/movie/week?language=en-US
     */
    @GET("/3/trending/movie/{time_window}")
    suspend fun fetchTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Query("language") language: String,
    ): MovieResponse

    /**
     * Get a list of movies that are currently in theatres.
     * [now_playing-movies](https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1)
     */
    @GET("/3/movie/now_playing")
    suspend fun fetchNowPlayingMovies(
        @Query("language") language: String,
        @Query("page") page:Int
    ):MovieResponse

    /**
     * Get a list of movies ordered by popularity.
     * [now_playing-movies](https://api.themoviedb.org/3/movie/popular?language=en-US&page=1)
     */
    @GET("/3/movie/popular")
    suspend fun fetchPopularMovies(
        @Query("language") language: String,
        @Query("page") page:Int
    ):MovieResponse

    /**
     * .
     * [recommendations-movies](https://api.themoviedb.org/3/movie/12/recommendations?language=en-US&page=1)
     */

    @GET("/3/movie/popular")
    suspend fun fetchRecommendedMovies(
        @Query("language") language: String,
        @Query("page") page:Int
    ):MovieResponse

}