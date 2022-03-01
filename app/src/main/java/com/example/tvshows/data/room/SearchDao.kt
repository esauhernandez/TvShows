package com.example.tvshows.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tvshows.data.model.QuerySearch
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuerySearch(item : QuerySearch): Completable

    @Query("SELECT * FROM QuerySearch")
    fun getAllQueries() : Single<List<QuerySearch>>

}