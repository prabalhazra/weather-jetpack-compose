package com.prabal.weather.data.local

import androidx.room.*
import com.prabal.weather.data.local.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM locationentity")
    fun getAllLocation(): Flow<List<LocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(locationEntity: LocationEntity)

    @Query("SELECT * FROM locationentity WHERE id = :id")
    suspend fun getLocation(id: Int): LocationEntity?

    @Delete
    suspend fun deleteLocation(locationEntity: LocationEntity)
}