package com.example.tvshows.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.tvshows.data.model.QuerySearch
import com.example.tvshows.domain.usecase.UseCaseRoomSearch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RoomViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    val TAG = "RoomViewModel"

    val listQueries = MutableLiveData<List<QuerySearch>>()
    var disposable: Disposable? = null

    fun getAllQueriesInDB() {
        disposable = UseCaseRoomSearch
            .getAllQueriesRoom(getApplication())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    listQueries.value = list
                },
                { error ->
                    Log.e(TAG, "ERROR:" + error.message)
                })
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RoomViewModel(application) as T
        }
    }
}