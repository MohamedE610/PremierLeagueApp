package com.example.premierleagueapp.features.teams.allteams.data.repository

import com.example.premierleagueapp.features.teams.allteams.data.source.local.TeamsLocalDataSource
import com.example.premierleagueapp.features.teams.allteams.domain.model.TeamModel
import com.example.premierleagueapp.features.teams.allteams.domain.repository.TeamsRepository
import io.reactivex.Single
import javax.inject.Inject

class TeamsRepositoryImpl @Inject constructor(
    private val teamsLocalDataSource: TeamsLocalDataSource
) : TeamsRepository {
    override fun getTeamsFromDB(offset: Int, limit: Int): Single<List<TeamModel>> {
        return teamsLocalDataSource.getTeamsFromDB(offset = offset, limit = limit)
    }


}