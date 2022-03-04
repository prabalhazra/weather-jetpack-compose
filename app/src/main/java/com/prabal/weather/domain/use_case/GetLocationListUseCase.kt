package com.prabal.weather.domain.use_case

import com.prabal.weather.data.local.entity.LocationEntity
import com.prabal.weather.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocationListUseCase(
    private val locationRepository: LocationRepository
) {
    operator fun invoke() : Flow<List<LocationEntity>> {
        return locationRepository.getAllLocation()
    }
}