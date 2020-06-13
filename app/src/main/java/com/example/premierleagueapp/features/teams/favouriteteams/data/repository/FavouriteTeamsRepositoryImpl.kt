package com.example.premierleagueapp.features.teams.favouriteteams.data.repository

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.teams.allteams.data.mapper.map
import com.example.premierleagueapp.features.teams.allteams.domain.model.TeamModel
import com.example.premierleagueapp.features.teams.favouriteteams.data.source.local.FavoriteTeamsLocalDataSource
import com.example.premierleagueapp.features.teams.favouriteteams.domain.repository.FavouriteTeamsRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class FavouriteTeamsRepositoryImpl @Inject constructor(
    private val favoriteTeamsLocalDataSource: FavoriteTeamsLocalDataSource
) : FavouriteTeamsRepository {
    override fun getFavouriteTeams(): Flowable<List<TeamModel>> {
        return favoriteTeamsLocalDataSource.getFavouriteTeams()
            .map { teams -> teams.map { team -> team.map() } }
    }
}