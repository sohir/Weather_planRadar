package com.idbs.weather.ui.cites

import com.idbs.weather.network.WeatherApiServices
import com.idbs.weather.ui.weatherDetails.CityWeatherResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CitesRepo @Inject constructor(val apiServices: WeatherApiServices){

    suspend fun getCities(): Flow<CitiesModel> {
        return flow {
            val list: ArrayList<CitiesModel.Cities> = ArrayList()
            list.add(CitiesModel.Cities(1,"London, UK"))
            list.add(CitiesModel.Cities(2,"Paris, FR"))
            list.add(CitiesModel.Cities(3,"Vienne,AUT"))
            val result = CitiesModel(list)
            emit(result)
        }.flowOn(Dispatchers.IO)

    }

    suspend fun getCityWeather(_cityName:String): Flow<CityWeatherResponseModel> {
        return flow {
            val result= apiServices.cityWeather(cityName = _cityName).await()
            emit(result)
        }.flowOn(Dispatchers.IO)

    }

}