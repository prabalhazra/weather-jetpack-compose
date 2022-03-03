package com.prabal.weather.presentation.weather_detail

import com.prabal.weather.domain.model.Weather

data class WeatherState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: String = ""
)