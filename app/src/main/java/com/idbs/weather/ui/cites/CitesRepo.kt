package com.idbs.weather.ui.cites

import com.idbs.weather.db.WeatherDB
import com.idbs.weather.network.WeatherApiServices
import com.idbs.weather.ui.weatherDetails.CityWeatherResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CitesRepo @Inject constructor(val apiServices: WeatherApiServices,val db: WeatherDB){

    suspend fun insertCity(city:CitiesModel.Cities){
       // withContext(Dispatchers.IO){
      val count:Int = 1
      //  CitiesModel.Cities(name = "London, UK $count++")
            db.getCityDao().insert(city)
       // }
    }
     fun getCities(): Flow<List<CitiesModel.Cities>> {
        return db.getCityDao().getAll()
/*        flow {
            val list: ArrayList<CitiesModel.Cities> = ArrayList()
            list.add(CitiesModel.Cities(1,"London, UK"))
            list.add(CitiesModel.Cities(2,"Paris, FR"))
            list.add(CitiesModel.Cities(3,"Vienne,AUT"))
            val result = CitiesModel(list)
            emit(db.getCityDao().getAll())
        }.flowOn(Dispatchers.IO)*/

    }

    suspend fun getCityWeather(_cityName:String): Flow<CityWeatherResponseModel> {
        return flow {
            val result= apiServices.cityWeather(cityName = _cityName).await()
            emit(result)
        }.flowOn(Dispatchers.IO)

    }

}