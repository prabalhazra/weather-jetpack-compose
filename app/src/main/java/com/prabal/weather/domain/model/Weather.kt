package com.prabal.weather.domain.model

import com.prabal.weather.data.remote.dto.CurrentConditions
import com.prabal.weather.data.remote.dto.NextDay

data class Weather(
    val region: String,
    val currentConditions: CurrentConditions,
    val next_days: List<NextDay>,
)
