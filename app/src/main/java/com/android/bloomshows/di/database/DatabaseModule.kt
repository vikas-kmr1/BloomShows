package com.android.bloomshows.di.database

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.android.bloomshows.data.room.AppDatabase
import com.android.bloomshows.data.room.GenreResponseConverter
import com.android.bloomshows.data.room.dao.MovieDao
import com.android.bloomshows.data.room.dao.TvDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideGenreResponseConverter(moshi: Moshi): GenreResponseConverter {
        return GenreResponseConverter(moshi)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
        genreResponseConverter: GenreResponseConverter
    ): AppDatabase{
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "TheMovieDB.db")
            .fallbackToDestructiveMigration()
            .addTypeConverter(genreResponseConverter)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.theMovieDao()
    }

    @Provides
    @Singleton
    fun provideTvDao(appDatabase: AppDatabase): TvDao {
        return appDatabase.theTvDao()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { context.preferencesDataStoreFile("user_data") }
        )
    }

}