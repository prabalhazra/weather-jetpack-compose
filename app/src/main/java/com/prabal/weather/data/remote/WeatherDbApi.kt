package com.prabal.weather.data.remote

import com.prabal.weather.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherDbApi {

    @GET("/data/weather/{location}")
    suspend fun getWeather(@Path("location") location: String): WeatherDto

}