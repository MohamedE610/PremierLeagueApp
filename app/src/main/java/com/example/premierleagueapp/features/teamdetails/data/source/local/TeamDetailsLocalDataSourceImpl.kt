package com.example.premierleagueapp.features.teamdetails.data.source.local

import com.example.premierleagueapp.core.data.source.local.dao.TeamsDao
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TeamDetailsLocalDataSourceImpl @Inject constructor(private val teamsDao: TeamsDao) :
    TeamDetailsLocalDataSource {
    override fun updateTeamDetails(teamDetails: TeamDetailsEntity): Completable {
        return Completable.fromCallable {
            teamsDao.updateTeam(team = teamDetails)
        }
    }

    override fun getTeamDetailsByIdFromDB(teamId: Int): Single<TeamDetailsEntity> {
        return Single.fromCallable {
            teamsDao.getTeamById(teamId)
        }
    }
}