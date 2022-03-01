package com.example.tvshows.data.room.repository

import android.app.Application
import com.example.tvshows.data.model.QuerySearch
import com.example.tvshows.data.room.SearchDao
import com.example.tvshows.data.room.SearchDatabase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchRoomRepository(application: Application)  {

    private val db = SearchDatabase.getInstance(application)
    private val searchRoomDao: SearchDao = db.getSearchDao()

    fun saveQueryRepo(query: QuerySearch): Single<Boolean> {
        return insertQueryInBd(query)
    }

    fun getAllQueriesRepo(): Single<List<QuerySearch>> {
        val queriesInnBD = getQueriesInBd()
        return queriesInnBD
    }

    private fun insertQueryInBd(query: QuerySearch): Single<Boolean> {
        query.timestamp = System.currentTimeMillis()
        val completable = searchRoomDao.insertQuerySearch(query)
        return completable
            .subscribeOn(Schedulers.computation())
            .toSingleDefault(true)
    }

    private fun getQueriesInBd(): Single<List<QuerySearch>> {
        val entities = searchRoomDao.getAllQueries()
        return entities
    }
}