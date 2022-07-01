package com.idbs.weather.ui.weatherDetails

import android.app.Application
import com.idbs.weather.base.BaseViewModel
import com.idbs.weather.base.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject
@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(val application: Application, val repo: WeatherRepo) : BaseViewModel() {

    private val _cityWeatherLoadingState = MutableStateFlow<NetworkState?>(NetworkState())
    val cityWeatherLoadingState: StateFlow<NetworkState?> = _cityWeatherLoadingState


     fun cityWeatherRequest(cityName:String)
    {
        viewModelScope.launch {
            try {
                _cityWeatherLoadingState.emit(NetworkState.LOADING)
                repo.getCityWeather(cityName).collect{
                    _cityWeatherLoadingState.emit(NetworkState.getLoaded(it))
                }
            }
            catch (throwable: Throwable) {
                _cityWeatherLoadingState.value=(NetworkState.getErrorMessage(throwable))
            }
        }
    }
}