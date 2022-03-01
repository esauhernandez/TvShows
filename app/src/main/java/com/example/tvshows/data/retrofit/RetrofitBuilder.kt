package com.example.tvshows.data.retrofit

import retrofit2.Retrofit

class RetrofitBuilder {

    companion object{
        private val mInstance= RetrofitBuilder()
        fun getBuilder():Retrofit.Builder= mInstance.getBuilder()
    }

    fun getBuilder():Retrofit.Builder{
        return Retrofit.Builder()
    }
}