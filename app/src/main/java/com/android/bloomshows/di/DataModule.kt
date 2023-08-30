package com.android.bloomshows.di

import com.android.bloomshows.data.local.preferences.UserPreferencesRepository
import com.android.bloomshows.data.local.preferences.UserPreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindUserPreferencesRepository(impl: UserPreferencesRepositoryImpl):UserPreferencesRepository
}