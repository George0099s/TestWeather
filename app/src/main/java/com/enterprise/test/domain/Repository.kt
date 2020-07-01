package com.enterprise.test.domain

import com.enterprise.test.data.App
import com.enterprise.test.data.network.callback.CallbackPics
import com.enterprise.test.data.network.callback.CallbackWeather
import com.enterprise.test.data.network.manager.NetworkManager
import javax.inject.Inject

class Repository {
    @Inject lateinit var networkManager: NetworkManager

    init {
        App.instance.component.inject(this)
    }

    fun getPics(page: Int, limit: Int, callback: CallbackPics){
        networkManager.getPics(page, limit, callback)
    }

    fun getWeather(callback: CallbackWeather, id: Int, lang: String){
        networkManager.getWeather(callback, id, lang)
    }
}