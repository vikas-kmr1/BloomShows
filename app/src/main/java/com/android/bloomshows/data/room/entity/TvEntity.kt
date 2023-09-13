package com.android.bloomshows.data.room.entity

import androidx.room.Entity


@Entity(tableName = "Tv", primaryKeys = ["id","page"] )
data class TvEntity(
    val id: Int,
    val page:Int,
    val totalPages:Int,
    val totalResults:Int,
    val adult: Boolean,
    val backdropPath: String?,
    val name: String?,  //name for tv
    val originalLanguage: String?,
    val originalName: String?,  //name for tv
    val overview: String?,
    val posterPath: String?,
    val mediaType: String?,
    val genreIds: List<Int>,
    val popularity: Double?,
    val firstAirDate: String?,  //Tv
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val category:String,
)


