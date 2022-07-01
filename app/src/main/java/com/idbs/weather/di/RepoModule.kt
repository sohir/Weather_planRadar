package com.idbs.weather.di

import com.idbs.weather.network.ApiServices
import com.idbs.weather.network.WeatherApiServices
import com.idbs.weather.ui.cites.CitesRepo
import com.idbs.weather.ui.league.LeagueRepo
import com.idbs.weather.ui.teams.TeamRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepoModule {
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
    fun provideCitiesListRepo(api:WeatherApiServices):CitesRepo{
        return CitesRepo(api)
    }
}