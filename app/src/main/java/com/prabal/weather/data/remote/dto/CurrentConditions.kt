package com.prabal.weather.data.remote.dto

data class CurrentConditions(
    val dayhour: String,
    val temp: Temp,
    val precip: String,
    val humidity: String,
    val wind: Wind,
    val iconURL: String,
    val comment: String
)