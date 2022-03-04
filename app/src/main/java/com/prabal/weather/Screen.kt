package com.prabal.weather

sealed class Screen(val route: String) {
    object AddLocationScreen : Screen("add_location_screen")
    object WeatherDetailScreen : Screen("weather_detail_screen")
}
