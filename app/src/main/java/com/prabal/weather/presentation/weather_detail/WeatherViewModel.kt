package com.prabal.weather.presentation.weather_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabal.weather.common.Constants
import com.prabal.weather.common.Resource
import com.prabal.weather.domain.use_case.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private var _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state

    private var _location = mutableStateOf(LocationState())
    val location: State<LocationState> = _location

    fun getLocation(q: String) {
        _location.value = LocationState(q)
    }

    fun getWeather(location: String) {

        getWeatherUseCase(location).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = WeatherState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = WeatherState(weather = result.data)
                }
                is Resource.Error -> {
                    _state.value = WeatherState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}