package com.example.premierleagueapp.features.teams.allteams.presentation.di

import com.example.premierleagueapp.core.data.source.local.db.PremierLeagueDB
import com.example.premierleagueapp.features.teams.allteams.data.repository.TeamsRepositoryImpl
import com.example.premierleagueapp.features.teams.allteams.data.source.local.TeamsLocalDataSource
import com.example.premierleagueapp.features.teams.allteams.data.source.local.TeamsLocalDataSourceImpl
import com.example.premierleagueapp.features.teams.allteams.domain.repository.TeamsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module()
abstract class AllTeamsModule {

        @Binds
        abstract fun bindTeamsLocalDataSource(teamsLocalDataSourceImpl: TeamsLocalDataSourceImpl): TeamsLocalDataSource

        @Binds
        abstract fun bindTeamsRepository(teamsRepositoryImpl: TeamsRepositoryImpl): TeamsRepository

}