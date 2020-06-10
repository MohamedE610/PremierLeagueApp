package com.example.premierleagueapp.features.loading.data.source.local

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import io.reactivex.Completable
import javax.inject.Inject

class LoadingLocalDataSourceImpl @Inject constructor(private val teamsDao: TeamsDao) :
    LoadingLocalDataSource {
    override fun refreshTeamsData(teams: List<TeamDetailsEntity>): Completable {
        return Completable.fromCallable {
            teamsDao.deleteAllTeams()
            teamsDao.insertTams(teams)
        }
    }
}