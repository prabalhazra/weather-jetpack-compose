package com.prabal.weather.di

import com.prabal.weather.common.Constants
import com.prabal.weather.data.remote.WeatherDbApi
import com.prabal.weather.data.repository.WeatherRepositoryImpl
import com.prabal.weather.domain.repository.WeatherRepository
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
}