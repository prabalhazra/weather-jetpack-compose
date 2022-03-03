package com.prabal.weather.data.repository

import com.prabal.weather.data.remote.WeatherDbApi
import com.prabal.weather.data.remote.dto.WeatherDto
import com.prabal.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherDbApi
): WeatherRepository {
    override suspend fun getWeather(location: String): WeatherDto {
        return api.getWeather(location)
    }

}