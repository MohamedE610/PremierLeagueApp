package com.example.premierleagueapp.features.teams.favouriteteams.data.source.local

import com.example.premierleagueapp.core.data.source.local.dao.TeamsDao
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class FavoriteTeamsLocalDataSourceImpl @Inject constructor(private val teamsDao: TeamsDao) :
    FavoriteTeamsLocalDataSource {
    override fun getFavouriteTeams(): Flowable<List<TeamDetailsEntity>> {
        return teamsDao.getFavouriteTeams()
    }
}