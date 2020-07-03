package com.enterprise.test.viewstate

import com.enterprise.test.data.network.pojo.PictureItem
import com.enterprise.test.data.network.pojo.Weather.Weather

sealed class WeatherViewState{
    object WeatherLoadingState: WeatherViewState()
    class WeatherLoadedState(val weather: Weather): WeatherViewState()
    class WeatherErrorState(val message: String): WeatherViewState()
}

sealed class WeatherIntent{
    class GetWeather(val id: Int,val lang: String) : WeatherIntent()
}

