package com.enterprise.test.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enterprise.test.data.App
import com.enterprise.test.data.network.callback.CallbackWeather
import com.enterprise.test.data.network.pojo.Weather.Weather
import com.enterprise.test.di.Component
import com.enterprise.test.di.DaggerComponent
import com.enterprise.test.domain.Repository
import com.enterprise.test.viewstate.WeatherIntent
import com.enterprise.test.viewstate.WeatherViewState
import javax.inject.Inject

class WeatherViewModel : ViewModel(), CallbackWeather {

    var weatherLiveData: MutableLiveData<WeatherViewState> = MutableLiveData()
    var weatherIntentLiveData: MutableLiveData<WeatherIntent> = MutableLiveData()


    fun getWeather(id: Int, lang: String) {
        weatherLiveData.value = WeatherViewState.WeatherLoadingState
        repository.getWeather(this, id, lang)
    }

    @Inject
    lateinit var repository: Repository


    init {
        App.instance.component.inject(this)
    }

    override fun onWeatherLoaded(weather: Weather) {
        weatherLiveData.postValue(WeatherViewState.WeatherLoadedState(weather))
    }

    override fun onError(message: String) {
        weatherLiveData.postValue(WeatherViewState.WeatherErrorState(message))
    }
}
