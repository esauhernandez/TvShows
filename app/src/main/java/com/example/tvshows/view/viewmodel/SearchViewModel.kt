package com.example.tvshows.view.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.tvshows.data.model.TvPrograms
import com.example.tvshows.domain.usecase.UseCaseRetrofitSearch
import com.example.tvshows.domain.usecase.interfaces.SearchCallback
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SearchViewModel : ViewModel(), SearchCallback, LifecycleObserver{

    val TAG="SearchViewModel"
    var mResponse = MutableLiveData<TvPrograms>()
    val DATE_FORMAT = "yyyy-MM-dd"

    fun performLoadTvPrograms(){
        val useCase = UseCaseRetrofitSearch.mInstance
        useCase.callbackSearch = this
        useCase.urlBase = "http://api.tvmaze.com/"
        useCase.country = "US"
        useCase.date = getCurrentDate().toString()
        useCase.runTvProgramsService()
    }

    fun performSearch(query: String){
        val useCase = UseCaseRetrofitSearch.mInstance
        useCase.callbackSearch = this
        useCase.urlBase = "http://api.tvmaze.com/search/"
        useCase.query = query
        useCase.runSearchService()
    }

    fun getCurrentDate(): String? {
        val dateFormat = SimpleDateFormat(DATE_FORMAT)
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
        val today: Date = Calendar.getInstance().getTime()
        return dateFormat.format(today)
    }

    override fun onSuccesCallResponse(responseCall: Call<TvPrograms>) {
        Log.d(TAG,"onSuccesCallResponse")
    }

    override fun onComplete() {
        Log.d(TAG,"onComplete")
    }

    override fun onSuccessResponse(response: Response<TvPrograms>) {
        mResponse.value = response.body()
    }

    override fun onSubscribe(d: Disposable) {
        Log.d(TAG,"onSubscribe ${d}")
    }

    override fun onError(e: Throwable) {
        Log.d(TAG,"onError ${e.stackTrace}")
    }

    override fun onFailureResponse(t: Throwable) {
        Log.d(TAG,"onError ${t.stackTrace}")
    }
}