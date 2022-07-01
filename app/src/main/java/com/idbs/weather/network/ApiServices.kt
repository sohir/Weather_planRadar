package com.idbs.weather.network

import com.idbs.weather.ui.league.LeagueResponsModel
import com.idbs.weather.ui.teams.TeamsListResponsModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiServices {
    @GET("competitions")
    fun getLeagueList(): Deferred<LeagueResponsModel>

    @GET("competitions/{leagueId}/teams")
    fun getTeamsList(
      @Path("leagueId") id: String
    ): Deferred<TeamsListResponsModel>
}