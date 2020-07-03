package com.enterprise.test.data.network.manager

import android.annotation.SuppressLint
import com.enterprise.test.data.network.api.API
import com.enterprise.test.data.network.callback.CallbackWeather
import com.enterprise.test.presentation.utils.Constants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherNetworkManager {

    @SuppressLint("CheckResult")
    fun getWeather(callback: CallbackWeather, id: Int, language: String) {
        val okClient = OkHttpClient.Builder()
            .addInterceptor { chain ->

                var request: Request = chain.request()
                val url: HttpUrl =
                    request.url().newBuilder()
                        .addQueryParameter(Constants.WEATHER_APPID_NAME, Constants.WEATHER_APPID)
                        .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()

        val service: API = API.createWeather()

        service.getWeather(id, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onWeatherLoaded(it)
            },
                {
                    callback.onError(it.localizedMessage)
                })
    }

}