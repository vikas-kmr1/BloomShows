package com.android.bloomshows.network.model.mapper


import com.android.bloomshows.data.room.entity.TvEntity
import com.android.bloomshows.network.model.movie.TvResponse
import com.android.bloomshows.network.model.movie.TvResultResponse

private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"

object TvEntityMapper : EntityMapper<TvResponse, List<TvEntity>> {

    override fun asEntity(domain: TvResponse,category:String): List<TvEntity> {
        return domain.results.map{  tv->

            TvEntity(
                id = tv.id,
                page = domain.page,
                totalPages = domain.totalPages,
                totalResults = domain.totalResults,
                adult = tv.adult,
                popularity = tv.popularity,
                posterPath = BASE_POSTER_PATH + tv.posterPath,
                originalLanguage = tv.originalLanguage,
                overview = tv.overview,
                backdropPath = BASE_BACKDROP_PATH + tv.backdropPath,
                video = tv.video,
                voteAverage = tv.voteAverage,
                voteCount = tv.voteCount,
                genreIds = tv.genreIds,
                mediaType = tv.mediaType,
                name = tv.name,
                originalName = tv.originalName,
                firstAirDate = tv.firstAirDate,
                category = category
                )
        }
    }

    override fun asDomain(entity: List<TvEntity>): TvResponse {
        val res = mutableListOf<TvResultResponse>()
        entity.map {tv->
            res.add(
                TvResultResponse(
                    id = tv.id,
                    adult = tv.adult,
                    popularity = tv.popularity,
                    posterPath = BASE_POSTER_PATH + tv.posterPath,
                    originalLanguage = tv.originalLanguage,
                    overview = tv.overview,
                    backdropPath = BASE_BACKDROP_PATH + tv.backdropPath,
                    video = tv.video,
                    voteAverage = tv.voteAverage,
                    voteCount = tv.voteCount,
                    genreIds = tv.genreIds,
                    mediaType = tv.mediaType,
                    name = tv.name,
                    originalName = tv.originalName,
                    firstAirDate = tv.firstAirDate
                )
            )
        }

        return TvResponse(
            page = entity.first().page,
            totalPages = entity.first().totalPages,
            totalResults = entity.first().totalResults,
            results = res
        )
    }
}

fun TvResponse.asEntity(category: String): List<TvEntity> {
    return TvEntityMapper.asEntity(this,category)
}

fun List<TvEntity>?.asDomain(): TvResponse {
    return TvEntityMapper.asDomain(this.orEmpty())
}

