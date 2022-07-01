package com.idbs.weather.ui.cites

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
class CitesViewModel @Inject constructor(val application: Application, val repo: CitesRepo) : BaseViewModel() {
    private val _citesLoadingState = MutableStateFlow<NetworkState?>(NetworkState())
    val citiesLoadingState: StateFlow<NetworkState?> = _citesLoadingState

    private val _cityWeatherLoadingState = MutableStateFlow<NetworkState?>(NetworkState())
    val cityWeatherLoadingState: StateFlow<NetworkState?> = _cityWeatherLoadingState
init {
    citiesRequest()
    cityWeatherRequest("London")
}
    private fun citiesRequest()
    {
        viewModelScope.launch {
            try {
                _citesLoadingState.emit(NetworkState.LOADING)
                repo.getCities().collect{
                    _citesLoadingState.emit(NetworkState.getLoaded(it))
                }
            }
            catch (throwable: Throwable) {
                _citesLoadingState.value=(NetworkState.getErrorMessage(throwable))
            }
        }
    }

    private fun cityWeatherRequest(cityName:String)
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