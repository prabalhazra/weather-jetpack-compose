package com.prabal.weather.data.remote.dto

data class NextDay(
    val day: String,
    val comment: String,
    val max_temp: Temp,
    val min_temp: Temp,
    val iconURL: String
)