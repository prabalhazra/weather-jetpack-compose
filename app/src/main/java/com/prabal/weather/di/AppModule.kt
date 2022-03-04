package com.prabal.weather.di

import android.app.Application
import androidx.room.Room
import com.prabal.weather.common.Constants
import com.prabal.weather.data.local.LocationDatabase
import com.prabal.weather.data.remote.WeatherDbApi
import com.prabal.weather.data.repository.LocationRepositoryImpl
import com.prabal.weather.data.repository.WeatherRepositoryImpl
import com.prabal.weather.domain.repository.LocationRepository
import com.prabal.weather.domain.repository.WeatherRepository
import com.prabal.weather.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherDbApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherDbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherDbApi): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesLocationDatabase(app: Application) : LocationDatabase {
        return Room.databaseBuilder(
            app,
            LocationDatabase::class.java,
            LocationDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesLocationRepository(db: LocationDatabase): LocationRepository {
        return LocationRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun providesLocationUseCases(repository: LocationRepository): LocationUseCases {
        return LocationUseCases(
            getLocationListUseCase = GetLocationListUseCase(repository),
            getLocationUseCase = GetLocationUseCase(repository),
            addLocationUseCase = AddLocationUseCase(repository),
            deleteLocationUseCase = DeleteLocationUseCase(repository)
        )
    }
}