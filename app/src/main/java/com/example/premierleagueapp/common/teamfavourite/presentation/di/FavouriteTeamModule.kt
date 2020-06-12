package com.example.premierleagueapp.common.teamfavourite.presentation.di

import com.example.premierleagueapp.common.teamfavourite.data.repository.TeamFavouriteRepositoryImpl
import com.example.premierleagueapp.common.teamfavourite.data.source.local.TeamFavouriteLocalDataSource
import com.example.premierleagueapp.common.teamfavourite.data.source.local.TeamFavouriteLocalDataSourceImpl
import com.example.premierleagueapp.common.teamfavourite.domain.repository.TeamFavouriteRepository
import com.example.premierleagueapp.core.data.source.local.db.PremierLeagueDB
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class FavouriteTeamModule {
    @Binds
    abstract fun bindTeamFavouriteLocalDataSource(teamFavouriteLocalDataSourceImpl: TeamFavouriteLocalDataSourceImpl): TeamFavouriteLocalDataSource

    @Binds
    abstract fun bindTeamFavouriteRepository(teamFavouriteRepository: TeamFavouriteRepositoryImpl): TeamFavouriteRepository
}