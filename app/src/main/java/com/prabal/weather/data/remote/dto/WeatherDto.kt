package com.prabal.weather.data.remote.dto

import com.prabal.weather.domain.model.Weather

data class WeatherDto(
    val region: String,
    val currentConditions: CurrentConditions,
    val next_days: List<NextDay>,
    val contact_author: ContactAuthor,
    val data_source: String
)

fun WeatherDto.toWeather() : Weather {
    return Weather(
        region = region,
        currentConditions = currentConditions,
        next_days = next_days
    )
}