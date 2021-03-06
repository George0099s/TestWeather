package com.enterprise.test.data.network.api

import android.provider.SyncStateContract
import com.enterprise.test.data.network.pojo.Picture
import com.enterprise.test.data.network.pojo.Weather.Weather
import com.enterprise.test.presentation.utils.Constants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("list")
    fun getPics(@Query("page") page: Int, @Query("limit") limit: Int): Observable<Picture>


    @GET("?units=metric")
    fun getWeather(
        @Query("id") id: Int,
        @Query("lang") lang: String
    ): Observable<Weather>


    companion object FactoryPicture {
        fun createPicture(): API {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.PICTURE_BASE_URL)
                .build()

            return retrofit.create(API::class.java);
        }

        fun createWeather(): API {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->

                    var request: Request = chain.request()
                    val url: HttpUrl =
                        request.url().newBuilder()
                            .addQueryParameter(Constants.WEATHER_APPID_NAME, Constants.WEATHER_APPID)
                            .build()
                    request = request.newBuilder().url(url).build()
                    chain.proceed(request)
                }
                .build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(Constants.WEATHER_BASE_URL)
                .build()

            return retrofit.create(API::class.java);
        }
    }


}
