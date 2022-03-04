package com.prabal.weather.domain.use_case

data class LocationUseCases(
    val getLocationListUseCase: GetLocationListUseCase,
    val getLocationUseCase: GetLocationUseCase,
    val addLocationUseCase: AddLocationUseCase,
    val deleteLocationUseCase: DeleteLocationUseCase
)
