package com.example.premierleagueapp.features.teams.data.source.local

import com.example.premierleagueapp.core.data.source.local.dao.TeamsDao
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TeamsLocalDataSourceImpl @Inject constructor(
    private val teamsDao: TeamsDao
) : TeamsLocalDataSource {

    override fun getTeamsFromDB(offset: Int, limit: Int): Single<List<TeamDetailsEntity>> {
        return Single.fromCallable {
            teamsDao.getTeams(offset, limit)
        }
    }

    override fun markTeamAsFavourite(teamId: Int): Completable {
        return Completable.fromCallable {
            saveTeamFavourite(teamId = teamId, isFavourite = true)
        }
    }

    override fun markTeamAsUnFavourite(teamId: Int): Completable {
        return Completable.fromCallable {
            saveTeamFavourite(teamId = teamId, isFavourite = false)
        }
    }

    private fun saveTeamFavourite(teamId: Int, isFavourite: Boolean) {
        val team = teamsDao.getTeamById(teamId)
        team.isFavourite = isFavourite
        teamsDao.updateTeam(team)
    }

}