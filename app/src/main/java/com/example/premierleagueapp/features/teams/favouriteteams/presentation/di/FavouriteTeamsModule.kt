package com.example.premierleagueapp.features.teams.favouriteteams.presentation.di


import com.example.premierleagueapp.features.teams.favouriteteams.data.repository.FavouriteTeamsRepositoryImpl
import com.example.premierleagueapp.features.teams.favouriteteams.data.source.local.FavoriteTeamsLocalDataSource
import com.example.premierleagueapp.features.teams.favouriteteams.data.source.local.FavoriteTeamsLocalDataSourceImpl
import com.example.premierleagueapp.features.teams.favouriteteams.domain.repository.FavouriteTeamsRepository
import dagger.Binds
import dagger.Module


@Module()
abstract class FavouriteTeamsModule {

    @Binds
    abstract fun bindFavouriteTeamsLocalDataSource(favouriteTeamsLocalDataSourceImpl: FavoriteTeamsLocalDataSourceImpl): FavoriteTeamsLocalDataSource

    @Binds
    abstract fun bindFavouriteTeamsRepository(favouriteTeamsRepositoryImpl: FavouriteTeamsRepositoryImpl): FavouriteTeamsRepository

}