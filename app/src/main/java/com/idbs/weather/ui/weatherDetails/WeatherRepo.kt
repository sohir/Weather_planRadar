package com.idbs.weather.ui.weatherDetails

import com.idbs.weather.network.WeatherApiServices
import com.idbs.weather.ui.weatherDetails.CityWeatherResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepo @Inject constructor(val apiServices: WeatherApiServices){


    suspend fun getCityWeather(_cityName:String): Flow<CityWeatherResponseModel> {
        return flow {
            val result= apiServices.cityWeather(cityName = _cityName).await()
            emit(result)
        }.flowOn(Dispatchers.IO)

    }

}