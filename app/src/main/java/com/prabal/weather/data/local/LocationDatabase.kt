package com.prabal.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prabal.weather.data.local.entity.LocationEntity

@Database(
    entities = [LocationEntity::class],
    version = 2
)
abstract class LocationDatabase: RoomDatabase() {

    abstract val dao: LocationDao

    companion object {
        const val DATABASE_NAME = "location_db"
    }

}