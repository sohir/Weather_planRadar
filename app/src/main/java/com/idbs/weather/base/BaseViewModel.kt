package com.idbs.weather.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel : ViewModel() {
    internal val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    val job = SupervisorJob()
    internal val viewModelScope = CoroutineScope(job + Dispatchers.Main)


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
    fun emptyNetWorkStateValue(){
        _networkState.value=null
    }
}