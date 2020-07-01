package com.enterprise.test.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enterprise.test.data.App
import com.enterprise.test.data.network.callback.CallbackWeather
import com.enterprise.test.data.network.pojo.Weather.Weather
import com.enterprise.test.domain.Repository
import javax.inject.Inject

class WeatherViewModel : ViewModel(), CallbackWeather {

    var weatherLiveData: MutableLiveData<Weather> = MutableLiveData<Weather>()

    fun getWeather(id: Int, lang: String){
        repository.getWeather(this, id, lang)
    }
    @Inject
    lateinit var repository: Repository


    init {
        App.instance.component.inject(this)
    }

    override fun onWeatherLoaded(weather: Weather) {
        weatherLiveData.postValue(weather)
    }
}
