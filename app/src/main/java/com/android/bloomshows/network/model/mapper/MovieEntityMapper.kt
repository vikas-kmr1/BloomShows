package com.android.bloomshows.network.model.mapper


import com.android.bloomshows.data.room.entity.MovieEntity
import com.android.bloomshows.network.model.movie.MovieResponse
import com.android.bloomshows.network.model.movie.MovieResultResponse

private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"

object MovieEntityMapper : EntityMapper<MovieResponse, List<MovieEntity>> {

    override fun asEntity(domain: MovieResponse, category: String): List<MovieEntity> {
        return domain.results.map{  movie ->
            MovieEntity(
                id = movie.id,
                page = domain.page,
                totalPages = domain.totalPages,
                totalResults = domain.totalResults,
                adult = movie.adult,
                popularity = movie.popularity,
                posterPath = BASE_POSTER_PATH + movie.posterPath,
                originalLanguage = movie.originalLanguage,
                originalTitle = movie.originalTitle,
                title = movie.title,
                releaseDate = movie.releaseDate,
                overview = movie.overview,
                backdropPath = BASE_BACKDROP_PATH + movie.backdropPath,
                video = movie.video,
                voteAverage = movie.voteAverage,
                voteCount = movie.voteCount,
                genreIds = movie.genreIds,
                mediaType = movie.mediaType,
                category = category
                )
        }
    }

    override fun asDomain(entity: List<MovieEntity>): MovieResponse {
        val res = mutableListOf<MovieResultResponse>()
        entity.map {movie->
            res.add(
                MovieResultResponse(
                    id = movie.id,
                    adult = movie.adult,
                    popularity = movie.popularity,
                    posterPath = BASE_POSTER_PATH + movie.posterPath,
                    originalLanguage = movie.originalLanguage,
                    originalTitle = movie.originalTitle,
                    title = movie.title,
                    releaseDate = movie.releaseDate,
                    overview = movie.overview,
                    backdropPath = BASE_BACKDROP_PATH + movie.backdropPath,
                    video = movie.video,
                    voteAverage = movie.voteAverage,
                    voteCount = movie.voteCount,
                    genreIds = movie.genreIds,
                    mediaType = movie.mediaType,
                )
            )
        }

        return MovieResponse(
            page = entity[0].page,
            totalPages = entity[0].totalPages,
            totalResults = entity[0].totalResults,
            results = res
        )
    }
}

fun MovieResponse.asEntity(category:String): List<MovieEntity> {
    return MovieEntityMapper.asEntity(this,category)
}

fun List<MovieEntity>?.asDomain(): MovieResponse {
    return MovieEntityMapper.asDomain(this.orEmpty())
}

