package com.example.premierleagueapp.features.teamdetails.presentation.di

import com.example.premierleagueapp.core.data.source.remote.ServiceGenerator
import com.example.premierleagueapp.features.teamdetails.data.repository.TeamDetailsRepositoryImpl
import com.example.premierleagueapp.features.teamdetails.data.source.local.TeamDetailsLocalDataSource
import com.example.premierleagueapp.features.teamdetails.data.source.local.TeamDetailsLocalDataSourceImpl
import com.example.premierleagueapp.features.teamdetails.data.source.remote.TeamDetailsApi
import com.example.premierleagueapp.features.teamdetails.data.source.remote.TeamDetailsRemoteDataSource
import com.example.premierleagueapp.features.teamdetails.data.source.remote.TeamDetailsRemoteDataSourceImpl
import com.example.premierleagueapp.features.teamdetails.domain.repository.TeamDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class TeamDetailsModule {

    @Provides
    fun providesTeamDetailsApi() = ServiceGenerator().createService(TeamDetailsApi::class.java)

    @Module
    interface BindsModule {
        @Binds
        fun bindTeamDetailsRemoteDataSource(remoteDataSourceImpl: TeamDetailsRemoteDataSourceImpl): TeamDetailsRemoteDataSource

        @Binds
        fun bindTeamDetailsLocalDataSource(localDataSourceImpl: TeamDetailsLocalDataSourceImpl): TeamDetailsLocalDataSource

        @Binds
        fun bindTeamDetailsRepository(repository: TeamDetailsRepositoryImpl): TeamDetailsRepository
    }
}