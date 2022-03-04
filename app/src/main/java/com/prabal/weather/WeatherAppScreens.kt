package com.prabal.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prabal.weather.presentation.add_location.LocationScreen
import com.prabal.weather.presentation.weather_detail.WeatherScreen

@ExperimentalComposeUiApi
@Composable
fun WeatherAppScreens() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.AddLocationScreen.route
    ) {
        composable(Screen.AddLocationScreen.route) {
            LocationScreen(navController = navController)
        }
        composable(
            route = Screen.WeatherDetailScreen.route + "/{location}",
            arguments = listOf(
                navArgument(
                    name = "location"
                ) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            entry.arguments?.getString("location")
                ?.let { WeatherScreen(navController = navController, location = it) }
        }
    }
}