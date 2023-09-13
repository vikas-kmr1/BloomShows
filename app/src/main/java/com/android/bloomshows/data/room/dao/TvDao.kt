package com.android.bloomshows.data.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.bloomshows.data.room.entity.TvEntity

@Dao
interface TvDao{

    //Tv
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvList(movieList: List<TvEntity>)

    @Query("SELECT * FROM Tv")
    fun getTvList(): PagingSource<Int, TvEntity>

    @Query("SELECT COUNT(id) FROM Tv ")
    fun getTvCount():Int
    @Query("SELECT * FROM Tv WHERE id = :id")
    fun getTvMovie(id:String): TvEntity
    @Query("DELETE FROM Tv")
    suspend fun deleteAllTv()
}