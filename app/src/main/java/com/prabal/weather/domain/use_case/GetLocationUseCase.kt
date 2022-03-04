package com.prabal.weather.domain.use_case

import com.prabal.weather.domain.repository.LocationRepository

class GetLocationUseCase(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(id: Int) {
        locationRepository.getLocation(id)
    }

}