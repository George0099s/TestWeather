package com.enterprise.test.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enterprise.test.data.App
import com.enterprise.test.domain.Repository
import com.enterprise.test.viewstate.WeatherIntent
import com.enterprise.test.viewstate.WeatherViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherViewModel : ViewModel(){

    var weatherLiveData: MutableLiveData<WeatherViewState> = MutableLiveData()
    var weatherIntentLiveData: MutableLiveData<WeatherIntent> = MutableLiveData()


    @SuppressLint("CheckResult")
    fun getWeather(id: Int, lang: String) {
        weatherLiveData.value = WeatherViewState.WeatherLoadingState
        repository.getWeather(id, lang)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                weatherLiveData.postValue(WeatherViewState.WeatherLoadedState(it))
            },
                {
                    weatherLiveData.postValue(WeatherViewState.WeatherErrorState(it.localizedMessage))
                })
    }

    @Inject
    lateinit var repository: Repository


    init {
        App.instance.component.inject(this)
    }

}
