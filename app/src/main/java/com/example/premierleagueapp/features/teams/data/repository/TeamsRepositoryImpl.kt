package com.example.premierleagueapp.features.teams.data.repository

import com.example.premierleagueapp.features.teams.data.mapper.map
import com.example.premierleagueapp.features.teams.data.source.local.TeamsLocalDataSource
import com.example.premierleagueapp.features.teams.domain.model.TeamModel
import com.example.premierleagueapp.features.teams.domain.repository.TeamsRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TeamsRepositoryImpl @Inject constructor(
    private val teamsLocalDataSource: TeamsLocalDataSource
) : TeamsRepository {
    override fun getTeamsFromDB(offset: Int, limit: Int): Single<List<TeamModel>> {
        return teamsLocalDataSource.getTeamsFromDB(offset = offset, limit = limit)
            .map { teams -> teams.map { team -> team.map() } }
    }

    override fun markTeamAsFavourite(teamId: Int): Completable {
        return teamsLocalDataSource.markTeamAsFavourite(teamId)
    }

    override fun markTeamAsUnFavourite(teamId: Int): Completable {
        return teamsLocalDataSource.markTeamAsUnFavourite(teamId)
    }
}