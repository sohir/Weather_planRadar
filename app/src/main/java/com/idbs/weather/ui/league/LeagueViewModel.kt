package com.idbs.weather.ui.league

import android.app.Application
import android.util.Log
import com.idbs.weather.network.JsoneErrorParsing
import com.idbs.weather.network.LoadingState
import com.idbs.weather.utilit.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LeagueViewModel @Inject constructor(val application: Application,val repo: LeagueRepo) : BaseViewModel()  {

    val leagueList = repo.leagueList

    init {
        leagueRequest()
    }
        fun leagueRequest(){
            _networkState.value = (LoadingState.LOADING)
            viewModelScope.launch {
              try {
                  repo.getList()
                  Log.v("league"," league list ${leagueList.value?.count}")
              }catch (t:Throwable){
                  Log.v("league"," error throwable msg : $t")
                  if (t is HttpException){
                      val errorResponse = JsoneErrorParsing(t)
                      Log.v("league"," error HttpException msg : $errorResponse")
                      _networkState.value=(LoadingState.error(errorResponse?.message,errorResponse))
                  }
                  else {
                      _networkState                  }
              }finally {
                  _networkState.value = (LoadingState.LOADED)
              }
              }

            }
        }

