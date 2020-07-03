package com.enterprise.test.domain

import com.enterprise.test.data.App
import com.enterprise.test.data.network.callback.CallbackPics
import com.enterprise.test.data.network.callback.CallbackWeather
import com.enterprise.test.data.network.manager.PictureNetworkManager
import com.enterprise.test.data.network.manager.WeatherNetworkManager
import com.enterprise.test.di.Component
import com.enterprise.test.di.DaggerComponent
import javax.inject.Inject

class Repository {
    @Inject lateinit var networkManager: PictureNetworkManager
    @Inject lateinit var weatherNetworkManager: WeatherNetworkManager

    init {
        App.instance.component.inject(this)
    }

    fun getPics(page: Int, limit: Int, callback: CallbackPics){
        networkManager.getPics(page, limit, callback)
    }

    fun getWeather(callback: CallbackWeather, id: Int, lang: String){
        weatherNetworkManager.getWeather(callback, id, lang)
    }
}