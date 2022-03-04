package com.prabal.weather.presentation.add_location

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.prabal.weather.Screen
import com.prabal.weather.presentation.add_location.components.LocationCard
import com.prabal.weather.presentation.add_location.components.SearchView

@ExperimentalComposeUiApi
@Composable
fun LocationScreen(
    viewModel: LocationViewModel = hiltViewModel(),
    navController: NavController
) {
    val locationState = viewModel.location.value
    val keyboardController = LocalSoftwareKeyboardController.current

    val locationList = viewModel.locations.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchView(
                value = locationState.location,
                onValueChange = {
                    viewModel.updateLocation(it)
                },
                onClear = {
                    viewModel.clearLocation()
                },
                onDone = {
                    viewModel.storeLocation(locationState.location)
                    keyboardController?.hide()
                    viewModel.clearLocation()
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(locationList.value) { location ->
                    LocationCard(
                        locationEntity = location,
                        onClick = {
                            viewModel.deleteLocation(location)
                        },
                        onItemClick = {
                            navController.navigate(Screen.WeatherDetailScreen.route + "/${location.locationName}")
                        }
                    )
                }
            }
        }
    }
}