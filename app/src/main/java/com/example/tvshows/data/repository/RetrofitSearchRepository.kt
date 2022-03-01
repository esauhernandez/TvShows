package com.example.tvshows.data.repository

import com.example.tvshows.data.provider.RetrofitSearchProvider
import io.reactivex.Maybe
import retrofit2.Retrofit

class RetrofitSearchRepository {
    companion object{
        val mInstance= RetrofitSearchRepository()
        fun getQuerySearch(urlBase:String): Maybe<Retrofit> =
            mInstance.getQuerySearch(urlBase)
    }

    private fun getQuerySearch(urlBase:String):Maybe<Retrofit>  {
        return Maybe.fromCallable{ RetrofitSearchProvider.getQuerySearch(urlBase)}
    }
}