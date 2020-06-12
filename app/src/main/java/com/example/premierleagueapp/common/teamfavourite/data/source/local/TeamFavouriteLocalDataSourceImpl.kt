package com.example.premierleagueapp.common.teamfavourite.data.source.local


import com.example.premierleagueapp.core.data.source.local.dao.TeamsDao
import io.reactivex.Completable
import javax.inject.Inject

class TeamFavouriteLocalDataSourceImpl @Inject constructor(
    private val teamsDao: TeamsDao
) : TeamFavouriteLocalDataSource {

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
        team?.isFavourite = isFavourite
        team?.let { teamsDao.updateTeam(it) }
    }

}