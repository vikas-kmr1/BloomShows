package com.android.bloomshows.data.repository.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.bloomshows.data.room.AppDatabase
import com.android.bloomshows.data.room.entity.MovieEntity
import com.android.bloomshows.network.model.mapper.asEntity
import com.android.bloomshows.network.model.movie.MovieResponse
import com.android.bloomshows.network.services.the_movie_db.MovieApiService
import com.android.bloomshows.utils.MediaCategories
import com.android.bloomshows.utils.Time_Window

import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator constructor(
    private val movieApiService: MovieApiService,
    private val appDatabase: AppDatabase,
    private val timeWindow:Time_Window,
    private val language:String,
    private val category: MediaCategories
) : RemoteMediator<Int, MovieEntity>() {

//    override suspend fun initialize(): InitializeAction {
//        // Require that remote REFRESH is launched on initial load and succeeds before launching
//        // remote PREPEND / APPEND.
//        return InitializeAction.LAUNCH_INITIAL_REFRESH
//    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ):  MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    val key = state.pages.size.times(state.config.pageSize) / state.config.pageSize

                    // no more items to load.
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    lastItem.page + 1
                }
            }

            val movieResponse:MovieResponse =
                when(category){
                    MediaCategories.TRENDING -> movieApiService.fetchTrendingMovies(timeWindow = timeWindow.title,language=language)
                    MediaCategories.POPULAR -> movieApiService.fetchPopularMovies(page = loadKey,language= language)
                    MediaCategories.NOW_PLAYING -> movieApiService.fetchNowPlayingMovies(page = loadKey,language= language)
                    MediaCategories.RECOMMENDATIONS -> movieApiService.fetchTrendingMovies(timeWindow = timeWindow.title,language= language)
                }

            val movieEntities = movieResponse.asEntity(category = category.title)


            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    //appDatabase.theMovieDao().deleteAllMovie()
                }
                appDatabase.theMovieDao().insertMovieList(movieEntities)
            }

            MediatorResult.Success(endOfPaginationReached = movieResponse.results.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }


}