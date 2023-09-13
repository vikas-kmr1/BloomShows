package com.android.bloomshows.di.database

import com.android.bloomshows.data.repository.movie.MovieRepository
import com.android.bloomshows.data.repository.movie.MovieRepositoryImpl
import com.android.bloomshows.data.repository.preferences.UserPreferencesRepository
import com.android.bloomshows.data.repository.preferences.UserPreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {
    @Binds
    abstract fun bindUserPreferencesRepository(impl: UserPreferencesRepositoryImpl): UserPreferencesRepository
    @Binds
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository
}