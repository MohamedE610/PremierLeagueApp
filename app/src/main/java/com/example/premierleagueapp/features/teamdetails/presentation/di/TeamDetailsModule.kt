package com.example.premierleagueapp.features.teamdetails.presentation.di

import com.example.premierleagueapp.features.teamdetails.data.repository.TeamDetailsRepositoryImpl
import com.example.premierleagueapp.features.teamdetails.data.source.local.TeamDetailsLocalDataSource
import com.example.premierleagueapp.features.teamdetails.data.source.local.TeamDetailsLocalDataSourceImpl
import com.example.premierleagueapp.features.teamdetails.domain.repository.TeamDetailsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class TeamDetailsModule {
    @Binds
    abstract fun bindTeamDetailsLocalDataSource(localDataSourceImpl: TeamDetailsLocalDataSourceImpl): TeamDetailsLocalDataSource

    @Binds
    abstract fun bindTeamDetailsRepository(repository: TeamDetailsRepositoryImpl): TeamDetailsRepository

}