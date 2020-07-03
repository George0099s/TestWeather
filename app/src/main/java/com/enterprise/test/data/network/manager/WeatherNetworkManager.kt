package com.enterprise.test.data.network.manager

import android.annotation.SuppressLint
import com.enterprise.test.data.network.api.API
import com.enterprise.test.data.network.pojo.Weather.Weather
import io.reactivex.Observable

class WeatherNetworkManager {

    @SuppressLint("CheckResult")
    fun getWeather(id: Int, language: String): Observable<Weather> {
        return API.createWeather().getWeather(id, language)
    }

}