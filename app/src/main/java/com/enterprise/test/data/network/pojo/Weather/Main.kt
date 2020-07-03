package com.enterprise.test.data.network.pojo.Weather


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like")
    val feelsLike: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("pressure")
    val pressure: String,
    @SerializedName("temp")
    val temp: String,
    @SerializedName("temp_max")
    val tempMax: String,
    @SerializedName("temp_min")
    val tempMin: String
)