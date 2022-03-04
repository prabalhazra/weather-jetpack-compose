package com.prabal.weather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val locationName: String
)
