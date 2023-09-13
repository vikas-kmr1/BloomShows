package com.android.bloomshows.data.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.bloomshows.data.room.entity.MovieEntity

@Dao
interface MovieDao{

    //Movie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM Movie WHERE category = :category")
    fun getMovieList(category: String): PagingSource<Int, MovieEntity>

    @Query("SELECT COUNT(id) FROM Movie WHERE category = :category")
    fun getMovieCount(category: String):Int
    @Query("SELECT * FROM Movie WHERE id = :id")
    fun getMovieById(id:String): MovieEntity
    @Query("DELETE FROM Movie")
    suspend fun deleteAllMovie()
}