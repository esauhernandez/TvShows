package com.example.tvshows.data.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tvshows.data.model.QuerySearch


@Database(version = 1,entities = [QuerySearch::class])
abstract class SearchDatabase : RoomDatabase(){
    companion object {

        private const val DB_NAME = "QueriesSearch"
        private val DB_CLASS = SearchDatabase::class.java

        @Volatile
        private var INSTANCE: SearchDatabase? = null

        fun getInstance(application: Application): SearchDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(application).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(application: Application): SearchDatabase {
            val databaseBuilder = Room.databaseBuilder(application, DB_CLASS, DB_NAME)
            return databaseBuilder.run {
                build()
            }
        }

    }

    abstract fun getSearchDao() : SearchDao
}