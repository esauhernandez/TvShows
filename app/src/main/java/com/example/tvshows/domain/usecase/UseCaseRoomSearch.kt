package com.example.tvshows.domain.usecase

import android.app.Application
import com.example.tvshows.data.model.QuerySearch
import com.example.tvshows.data.room.repository.SearchRoomRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UseCaseRoomSearch {

    companion object{
        private val mInstance= UseCaseRoomSearch()
        fun saveQueryRoom(query: QuerySearch, application: Application) =
            mInstance.saveQueryRoom(query, application)
        fun getAllQueriesRoom(application: Application): Observable<List<QuerySearch>> =
            mInstance.getAllQueriesRoom(application)
    }

    fun saveQueryRoom(query: QuerySearch, application: Application) {
        val repository = SearchRoomRepository(application)
        val savingOperation = repository.saveQueryRepo(query)
        savingOperation
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getAllQueriesRoom(application: Application): Observable<List<QuerySearch>> {
        val repository = SearchRoomRepository(application)
        val single = repository.getAllQueriesRepo()
        return single
            .toObservable()
            .map {
                it
            }
    }
}