package com.android.bloomshows.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.bloomshows.data.room.dao.MovieDao
import com.android.bloomshows.data.room.dao.TvDao
import com.android.bloomshows.data.room.entity.MovieEntity
import com.android.bloomshows.data.room.entity.TvEntity


@Database(entities = [(MovieEntity::class),(TvEntity::class)], exportSchema = false, version = 1)
@TypeConverters(value = [GenreResponseConverter::class])
abstract class AppDatabase: RoomDatabase() {
    abstract fun theMovieDao(): MovieDao
    abstract fun theTvDao(): TvDao
}