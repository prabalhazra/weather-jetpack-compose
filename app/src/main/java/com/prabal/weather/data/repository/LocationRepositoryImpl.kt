package com.prabal.weather.data.repository

import com.prabal.weather.data.local.LocationDao
import com.prabal.weather.data.local.entity.LocationEntity
import com.prabal.weather.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class LocationRepositoryImpl(
    private val dao: LocationDao
) : LocationRepository {
    override fun getAllLocation(): Flow<List<LocationEntity>> {
        return dao.getAllLocation()
    }

    override suspend fun getLocation(id: Int): LocationEntity? {
        return dao.getLocation(id)
    }

    override suspend fun addLocation(locationEntity: LocationEntity) {
        return dao.addLocation(locationEntity)
    }

    override suspend fun deleteLocation(locationEntity: LocationEntity) {
        return dao.deleteLocation(locationEntity)
    }
}