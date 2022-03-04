package com.prabal.weather.domain.repository

import com.prabal.weather.data.local.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getAllLocation() : Flow<List<LocationEntity>>

    suspend fun getLocation(id: Int): LocationEntity?

    suspend fun addLocation(locationEntity: LocationEntity)

    suspend fun deleteLocation(locationEntity: LocationEntity)

}