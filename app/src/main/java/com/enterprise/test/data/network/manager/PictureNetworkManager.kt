package com.enterprise.test.data.network.manager

import android.annotation.SuppressLint
import android.util.Log
import com.enterprise.test.data.network.api.API
import com.enterprise.test.data.network.callback.CallbackPics
import com.enterprise.test.data.network.callback.CallbackWeather
import com.enterprise.test.data.network.pojo.Picture
import com.enterprise.test.data.network.pojo.Weather.Weather
import com.enterprise.test.presentation.utils.Constants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.functions.Action1
import java.io.IOException


class PictureNetworkManager {

    @SuppressLint("CheckResult")
    fun getPics(page: Int, limit: Int, callback: CallbackPics) {
        val service: API =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.PICTURE_BASE_URL)
                .build().create(API::class.java)

        service.getPics(page, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("!23", it.size.toString())
                callback.onPicsLoaded(it)
            }, {
                callback.onPicsLoadedFailure(it.localizedMessage)
            })
    }
}