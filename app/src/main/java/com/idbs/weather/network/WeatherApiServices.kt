package com.idbs.weather.network

import com.idbs.weather.ui.weatherDetails.CityWeatherResponseModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiServices {
@GET("data/2.5/weather")
fun cityWeather(
    @Query("q")cityName:String,
    @Query("appid")apiKey:String = "f5cb0b965ea1564c50c6f1b74534d823"
    ): Deferred<CityWeatherResponseModel>
}