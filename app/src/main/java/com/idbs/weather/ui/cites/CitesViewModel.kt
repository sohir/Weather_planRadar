package com.idbs.weather.ui.cites

import android.app.Application
import android.util.Log
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

    private val _insertCityLoadingState = MutableStateFlow<NetworkState?>(NetworkState())
    val insertCityLoadingState: StateFlow<NetworkState?> = _insertCityLoadingState


    init {
        citiesRequest()
    }

    fun insertCity(city:CitiesModel.Cities){
        viewModelScope.launch {
            try {
                _insertCityLoadingState.emit(NetworkState.LOADING)
                repo.insertCity(city)
            }catch (throwable: Throwable) {
                _insertCityLoadingState.value=(NetworkState.getErrorMessage(throwable))
            }

        }

    }
     fun citiesRequest()
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

}