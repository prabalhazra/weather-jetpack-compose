package com.prabal.weather.domain.use_case

import com.prabal.weather.common.Resource
import com.prabal.weather.data.remote.dto.toWeather
import com.prabal.weather.domain.model.Weather
import com.prabal.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(location: String): Flow<Resource<Weather>> = flow {
        try {
            emit(Resource.Loading())
            val weather = repository.getWeather(location).toWeather()
            emit(Resource.Success(weather))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
        catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}