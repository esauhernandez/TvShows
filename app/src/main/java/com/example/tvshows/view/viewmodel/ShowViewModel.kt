package com.example.tvshows.view.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.tvshows.data.model.Persons
import com.example.tvshows.data.model.Show
import com.example.tvshows.domain.usecase.UseCaseRetrofitLoadShow
import com.example.tvshows.domain.usecase.interfaces.ShowCallback
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Response

class ShowViewModel : ViewModel(), ShowCallback, LifecycleObserver{

    val TAG="ShowViewModel"
    var mResponse = MutableLiveData<Show>()
    var mResponseTalents = MutableLiveData<Persons>()

    fun performLoadShow(idShow: Int){
        val useCase = UseCaseRetrofitLoadShow.mInstance
        useCase.callbackShow = this
        useCase.urlBase = "https://api.tvmaze.com/shows/"
        useCase.idShow = idShow
        useCase.runShowService()
    }

    fun performLoadTalentsShow(idShow: Int){
        val useCase = UseCaseRetrofitLoadShow.mInstance
        useCase.callbackShow = this
        useCase.urlBase = "https://api.tvmaze.com/shows/"
        useCase.idShow = idShow
        useCase.runShowTalentsService()
    }

    override fun onSuccesCallResponse(responseCall: Call<Show>) {
        Log.d(TAG,"onSuccesCallResponse")
    }

    override fun onSuccesTalentsCallResponse(responseCall: Call<Persons>) {
        Log.d(TAG,"onSuccesCallResponse")
    }

    override fun onComplete() {
        Log.d(TAG,"onComplete")
    }

    override fun onSuccessResponse(response: Response<Show>) {
        mResponse.value = response.body()
    }

    override fun onSuccessTalentsResponse(response: Response<Persons>) {
        mResponseTalents.value = response.body()
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