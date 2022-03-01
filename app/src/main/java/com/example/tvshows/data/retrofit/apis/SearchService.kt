package com.example.tvshows.data.retrofit.apis

import com.example.tvshows.data.model.Persons
import com.example.tvshows.data.model.Show
import com.example.tvshows.data.model.TvPrograms
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {
    @GET("schedule")
    fun getServiceTvPrograms(@Query("country") country: String?, @Query("date") date: String?): Call<TvPrograms>

    @GET("shows")
    fun getServiceSearch(@Query("q") country: String?): Call<TvPrograms>

    @GET("{idShow}")
    fun getShow(@Path("idShow") idShow: String?): Call<Show>

    @GET("{idShow}/cast")
    fun getShowTalents(@Path("idShow") idShow: String?): Call<Persons>
}