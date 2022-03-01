package com.example.tvshows.domain.usecase

import com.example.tvshows.data.repository.RetrofitSearchRepository
import com.example.tvshows.domain.mapper.SearchResponseMapper
import com.example.tvshows.domain.usecase.interfaces.SearchCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UseCaseRetrofitSearch {

    lateinit var callbackSearch: SearchCallback
    var country: String = ""
    var date: String = ""
    var query: String = ""
    lateinit var urlBase:String
    companion object{
        val mInstance = UseCaseRetrofitSearch()
    }

    fun runTvProgramsService(): Disposable {
        return RetrofitSearchRepository.getQuerySearch(urlBase)
            .map { retrofit->
                SearchResponseMapper.mapper(retrofit, country, date, callbackSearch ) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {callbackSearch.onSuccesCallResponse(it)}
                ,{callbackSearch.onError(it)}
                ,{callbackSearch.onComplete()})
    }

    fun runSearchService(): Disposable {
        return RetrofitSearchRepository.getQuerySearch(urlBase)
            .map { retrofit->
                SearchResponseMapper.mapperSearch(retrofit, query, callbackSearch ) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {callbackSearch.onSuccesCallResponse(it)}
                ,{callbackSearch.onError(it)}
                ,{callbackSearch.onComplete()})
    }

}