package com.enterprise.test.data.network.manager

import android.util.Log
import com.enterprise.test.data.network.api.API
import com.enterprise.test.data.network.callback.CallbackPics
import com.enterprise.test.data.network.callback.CallbackWeather
import com.enterprise.test.data.network.pojo.Picture
import com.enterprise.test.data.network.pojo.Weather.Weather
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class NetworkManager {



    fun getPics(page: Int, limit: Int, callback: CallbackPics) {
        val service: API =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://picsum.photos/v2/")
                .build().create(API::class.java)

        service.getPics(page, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Picture?> {
                override fun onComplete() {
                    Log.d("123", "onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d("123", "onSubscribe")

                }

                override fun onNext(picture: Picture) {
                    callback.onPicsLoaded(picture)
                }

                override fun onError(e: Throwable) {
                    Log.d("123", "onError" + e.message)
                }

            })
    }
    fun getWeather(callback: CallbackWeather, id: Int, language: String) {
        val okClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request: Request = chain.request()
                val url: HttpUrl =
                    request.url().newBuilder().addQueryParameter("appid", "c35880b49ff95391b3a6d0edd0c722eb").build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()

        val service: API =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://picsum.photos/v2/")
                .client(okClient)
                .build().create(API::class.java)

        service.getWeather(id, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Weather?> {
                override fun onComplete() {
                    Log.d("123", "onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d("123", "onSubscribe")

                }

                override fun onNext(weather: Weather) {
                    Log.d("123", "onNext" + weather.main)
                    callback.onWeatherLoaded(weather)
                }

                override fun onError(e: Throwable) {
                    Log.d("123", "onError" + e.message)
                }

            })
    }
}