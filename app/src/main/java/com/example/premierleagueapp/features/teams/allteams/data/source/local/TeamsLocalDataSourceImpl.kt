package com.example.premierleagueapp.features.teams.allteams.data.source.local


import com.example.premierleagueapp.core.data.source.local.dao.TeamsDao
import com.example.premierleagueapp.features.teams.allteams.data.mapper.map
import com.example.premierleagueapp.features.teams.allteams.domain.model.TeamModel
import io.reactivex.Single
import javax.inject.Inject

class TeamsLocalDataSourceImpl @Inject constructor(
    private val teamsDao: TeamsDao
) : TeamsLocalDataSource {
    override fun getTeamsFromDB(offset: Int, limit: Int): Single<List<TeamModel>> {
        return Single.fromCallable {
            teamsDao.getTeams(offset = offset, limit = limit).map { team -> team.map() }
        }
    }
}