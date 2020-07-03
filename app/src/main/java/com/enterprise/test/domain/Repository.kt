package com.enterprise.test.domain

import com.enterprise.test.data.App
import com.enterprise.test.data.network.manager.PictureNetworkManager
import com.enterprise.test.data.network.manager.WeatherNetworkManager
import com.enterprise.test.data.network.pojo.Picture
import com.enterprise.test.data.network.pojo.Weather.Weather
import io.reactivex.Observable
import javax.inject.Inject

class Repository {
    @Inject lateinit var networkManager: PictureNetworkManager
    @Inject lateinit var weatherNetworkManager: WeatherNetworkManager

    init {
        App.instance.component.inject(this)
    }

    fun getPics(page: Int, limit: Int) : Observable<Picture> {
        return networkManager.getPics(page, limit)
    }

    fun getWeather( id: Int, lang: String) : Observable<Weather>{
        return weatherNetworkManager.getWeather( id, lang)
    }
}