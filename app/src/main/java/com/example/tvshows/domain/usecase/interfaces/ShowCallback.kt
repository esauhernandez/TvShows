package com.example.tvshows.domain.usecase.interfaces

import com.example.tvshows.data.model.Persons
import com.example.tvshows.data.model.Show
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Response

interface ShowCallback {
    fun onSuccesCallResponse(responseCall: Call<Show>)
    fun onSuccesTalentsCallResponse(responseCall: Call<Persons>)
    fun onComplete()
    fun onSuccessResponse(response: Response<Show>)
    fun onSuccessTalentsResponse(response: Response<Persons>)
    fun onSubscribe(d: Disposable)
    fun onError(e: Throwable)
    fun onFailureResponse(t: Throwable)
}