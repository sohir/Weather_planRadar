package com.idbs.weather.di

import android.app.Application
import com.idbs.weather.db.WeatherDB
import com.idbs.weather.network.ApiServices
import com.idbs.weather.network.WeatherApiServices
import com.idbs.weather.ui.cites.CitesRepo
import com.idbs.weather.ui.league.LeagueRepo
import com.idbs.weather.ui.teams.TeamRepo
import com.idbs.weather.ui.weatherDetails.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Singleton

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepoModule {
    @Provides
    fun DataBaseRoom(application: Application): WeatherDB {
        return WeatherDB(application)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideLeagueListRepo(api:ApiServices):LeagueRepo{
        return LeagueRepo(api)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideTeamListRepo(api:ApiServices):TeamRepo{
        return TeamRepo(api)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideCitiesListRepo(api:WeatherApiServices,db:WeatherDB):CitesRepo{
        return CitesRepo(api,db)
    }
    @EntryPoint
    @InstallIn(ActivityRetainedComponent::class)
    interface PreferenceEntryPointRoom {
        val weatherDB: WeatherDB
    }
    @Provides
    @ActivityRetainedScoped
    fun provideWeatherDetailsRepo(api:WeatherApiServices):WeatherRepo{
        return WeatherRepo(api)
    }




}