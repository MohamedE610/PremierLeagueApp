package com.example.premierleagueapp.features.loading.data.source.local

import com.example.premierleagueapp.core.data.source.local.dao.TeamsDao
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import io.reactivex.Completable
import javax.inject.Inject

class LoadingLocalDataSourceImpl @Inject constructor(private val teamsDao: TeamsDao) :
    LoadingLocalDataSource {
    override fun refreshTeamsData(teams: List<TeamDetailsEntity>): Completable {
        return Completable.fromCallable {
            val favouriteTeams = teamsDao.getFavouriteTeams().blockingFirst()
            val updatedTeams = updateFavouriteTeams(favouriteTeams, teams)
            teamsDao.insertTeams(updatedTeams)
        }
    }

    private fun updateFavouriteTeams(
        favouriteTeams: List<TeamDetailsEntity>?,
        teams: List<TeamDetailsEntity>
    ): List<TeamDetailsEntity> {

        if (favouriteTeams.isNullOrEmpty())
            return teams

        val teamsHashMap = HashMap<Int/*teamId*/, TeamDetailsEntity>()
        for (team in teams)
            teamsHashMap[team.id] = team

        for (team in favouriteTeams)
            teamsHashMap[team.id]?.isFavourite = team.isFavourite

        return teamsHashMap.values.toList()
    }
}