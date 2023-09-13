package com.android.bloomshows.di.network

import com.android.bloomshows.data.repository.movie.MovieRepository
import com.android.bloomshows.data.repository.movie.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindModule {
//    @Binds
//    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

}