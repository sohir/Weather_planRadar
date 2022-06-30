package com.idbs.weather.ui.teams

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
class TeamViewModel @Inject constructor(
    val application: Application,val repo: TeamRepo) : BaseViewModel()  {

    val teamList = repo.teamList
        fun teamRequest(id:String){
            _networkState.value = (LoadingState.LOADING)
            viewModelScope.launch {
              try {
                  repo.getTeamList(id)
                  Log.v("league"," team list ${teamList.value?.teams?.size}")
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

