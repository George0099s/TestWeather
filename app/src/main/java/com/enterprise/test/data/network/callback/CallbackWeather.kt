package com.enterprise.test.data.network.callback

import com.enterprise.test.data.network.pojo.Weather.Weather

interface CallbackWeather {
    fun onWeatherLoaded(weather: Weather)
}