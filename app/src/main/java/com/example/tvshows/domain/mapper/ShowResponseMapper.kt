package com.example.tvshows.domain.mapper

import com.example.tvshows.data.model.Persons
import com.example.tvshows.data.model.Show
import com.example.tvshows.data.retrofit.apis.SearchService
import com.example.tvshows.domain.usecase.interfaces.ShowCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ShowResponseMapper {

    var responseSe: Show? = null
    var responseTalents: Persons? = null
    companion object{
        val mInstance=ShowResponseMapper()
        fun mapper(retrofit: Retrofit, idShow: Int,
                   callbackTvPrograms: ShowCallback) =
            mInstance.mapper(retrofit, idShow, callbackTvPrograms)

        fun mapperTalent(retrofit: Retrofit, idShow: Int,
                   callbackTalent: ShowCallback) =
            mInstance.mapperTalent(retrofit, idShow, callbackTalent)

    }


    private fun mapper(retrofit: Retrofit, idShow: Int, callback: ShowCallback): Call<Show> {
        val reService = retrofit.create(SearchService::class.java)
        val answerRECall = reService.getShow(idShow.toString())
        try {
            answerRECall.enqueue(object : Callback<Show> {
                override fun onResponse(
                    call: Call<Show>,
                    response: Response<Show>
                ) {
                    if (response.isSuccessful) {
                        responseSe = response.body()
                    }
                    callback.onSuccessResponse(response)
                }

                override fun onFailure(
                    call: Call<Show>,
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

    private fun mapperTalent(retrofit: Retrofit, idShow: Int, callback: ShowCallback): Call<Persons> {
        val reService = retrofit.create(SearchService::class.java)
        val answerRECall = reService.getShowTalents(idShow.toString())
        try {
            answerRECall.enqueue(object : Callback<Persons> {
                override fun onResponse(
                    call: Call<Persons>,
                    response: Response<Persons>
                ) {
                    if (response.isSuccessful) {
                        responseTalents = response.body()
                    }
                    callback.onSuccessTalentsResponse(response)
                }

                override fun onFailure(
                    call: Call<Persons>,
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