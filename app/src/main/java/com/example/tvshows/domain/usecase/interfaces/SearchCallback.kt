package com.example.tvshows.domain.usecase.interfaces

import com.example.tvshows.data.model.TvPrograms
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Response

interface SearchCallback {
    fun onSuccesCallResponse(responseCall: Call<TvPrograms>)
    fun onComplete()
    fun onSuccessResponse(response: Response<TvPrograms>)
    fun onSubscribe(d: Disposable)
    fun onError(e: Throwable)
    fun onFailureResponse(t: Throwable)
}