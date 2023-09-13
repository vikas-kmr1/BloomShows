package com.android.bloomshows.data.room.entity

import androidx.room.Entity


@Entity(tableName = "Movie", primaryKeys = ["id","category"] )
data class MovieEntity(
    val id: Int,
    val page:Int,
    val totalPages:Int,
    val totalResults:Int,
    val adult: Boolean,
    val backdropPath: String?,
    val title: String?,   //title for movie
    val originalLanguage: String?,
    val originalTitle: String?,   //title for movie
    val overview: String?,
    val posterPath: String?,
    val mediaType: String?,
    val genreIds: List<Int>,
    val popularity: Double?,
    val releaseDate: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val category: String
)


