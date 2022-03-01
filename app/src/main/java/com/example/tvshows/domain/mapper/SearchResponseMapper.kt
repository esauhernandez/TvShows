package com.example.tvshows.domain.mapper

import com.example.tvshows.data.model.TvPrograms
import com.example.tvshows.data.retrofit.apis.SearchService
import com.example.tvshows.domain.usecase.interfaces.SearchCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SearchResponseMapper {

    var responseSe: TvPrograms? = null
    companion object{
        val mInstance=SearchResponseMapper()
        fun mapper(retrofit: Retrofit, country: String, date: String,
                   callbackTvPrograms: SearchCallback) =
            mInstance.mapper(retrofit, country, date, callbackTvPrograms)

        fun mapperSearch(retrofit: Retrofit, query: String,
                   callbackTvPrograms: SearchCallback) =
            mInstance.mapperSearch(retrofit, query, callbackTvPrograms)
    }


    private fun mapper(retrofit: Retrofit, country: String, date: String, callback: SearchCallback ): Call<TvPrograms> {
        val reService = retrofit.create(SearchService::class.java)
        val answerRECall = reService.getServiceTvPrograms(country, date)
        try {
            answerRECall.enqueue(object : Callback<TvPrograms> {
                override fun onResponse(
                    call: Call<TvPrograms>,
                    response: Response<TvPrograms>
                ) {
                    if (response.isSuccessful) {
                        responseSe = response.body()
                    }
                    callback.onSuccessResponse(response)
                }

                override fun onFailure(
                    call: Call<TvPrograms>,
                    t: Throwable
                ) {
                    if (t.message != "") {
                    }
                    callback.onFailureResponse(t)
                }
            })
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }

        return answerRECall
    }

    private fun mapperSearch(retrofit: Retrofit, query: String, callback: SearchCallback ): Call<TvPrograms> {
        val reService = retrofit.create(SearchService::class.java)
        val answerRECall = reService.getServiceSearch(query)
        try {
            answerRECall.enqueue(object : Callback<TvPrograms> {
                override fun onResponse(
                    call: Call<TvPrograms>,
                    response: Response<TvPrograms>
                ) {
                    if (response.isSuccessful) {
                        responseSe = response.body()
                    }
                    callback.onSuccessResponse(response)
                }

                override fun onFailure(
                    call: Call<TvPrograms>,
                    t: Throwable
                ) {
                    if (t.message != "") {
                    }
                    callback.onFailureResponse(t)
                }
            })
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }

        return answerRECall
    }

}