package com.example.tvshows.domain.usecase

import com.example.tvshows.data.repository.RetrofitSearchRepository
import com.example.tvshows.domain.mapper.ShowResponseMapper
import com.example.tvshows.domain.usecase.interfaces.ShowCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UseCaseRetrofitLoadShow {

    lateinit var callbackShow: ShowCallback
    var idShow: Int = 0
    lateinit var urlBase:String
    companion object{
        val mInstance = UseCaseRetrofitLoadShow()
    }

    fun runShowService(): Disposable {
        return RetrofitSearchRepository.getQuerySearch(urlBase)
            .map { retrofit->
                ShowResponseMapper.mapper(retrofit, idShow, callbackShow ) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {callbackShow.onSuccesCallResponse(it)}
                ,{callbackShow.onError(it)}
                ,{callbackShow.onComplete()})
    }

    fun runShowTalentsService(): Disposable {
        return RetrofitSearchRepository.getQuerySearch(urlBase)
            .map { retrofit->
                ShowResponseMapper.mapperTalent(retrofit, idShow, callbackShow ) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {callbackShow.onSuccesTalentsCallResponse(it)}
                ,{callbackShow.onError(it)}
                ,{callbackShow.onComplete()})
    }

}