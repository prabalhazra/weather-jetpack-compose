package com.prabal.weather.presentation.add_location

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabal.weather.data.local.entity.LocationEntity
import com.prabal.weather.domain.use_case.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases
) : ViewModel() {

    private var _location = mutableStateOf(LocationState())
    val location: State<LocationState> = _location

    val locations = locationUseCases.getLocationListUseCase()

    fun updateLocation(location: String) {
        _location.value = LocationState(location = location)
    }

    fun clearLocation() {
        _location.value = LocationState(location = "")
    }

    fun storeLocation(location: String) = viewModelScope.launch {
        locationUseCases.addLocationUseCase(
            LocationEntity(
                locationName = location
            )
        )
    }

    fun deleteLocation(locationEntity: LocationEntity) = viewModelScope.launch {
        locationUseCases.deleteLocationUseCase(
            locationEntity = locationEntity
        )
    }
}