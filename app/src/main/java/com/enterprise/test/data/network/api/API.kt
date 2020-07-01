package com.enterprise.test.data.network.api

import com.enterprise.test.data.network.pojo.Picture
import com.enterprise.test.data.network.pojo.Weather.Weather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("list")
    fun getPics(@Query("page") page: Int, @Query("limit") limit: Int): Observable<Picture>


    @GET("https://api.openweathermap.org/data/2.5/weather?units=metric")
    fun getWeather(
        @Query("id") id: Int,
        @Query("lang") lang: String
    ): Observable<Weather>

}