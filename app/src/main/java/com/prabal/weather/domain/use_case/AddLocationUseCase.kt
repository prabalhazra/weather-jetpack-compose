package com.prabal.weather.domain.use_case

import com.prabal.weather.data.local.entity.LocationEntity
import com.prabal.weather.domain.repository.LocationRepository

class AddLocationUseCase(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(locationEntity: LocationEntity) {
        locationRepository.addLocation(locationEntity)
    }
}