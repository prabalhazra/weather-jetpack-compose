package com.prabal.weather.domain.repository

import com.prabal.weather.data.remote.dto.WeatherDto

interface WeatherRepository {

    suspend fun getWeather(location: String): WeatherDto
}