package com.example.premierleagueapp.common.teamfavourite.data.repository

import com.example.premierleagueapp.common.teamfavourite.domain.repository.TeamFavouriteRepository
import com.example.premierleagueapp.common.teamfavourite.data.source.local.TeamFavouriteLocalDataSource
import io.reactivex.Completable
import javax.inject.Inject

class TeamFavouriteRepositoryImpl @Inject constructor(
    private val teamFavouriteLocalDataSource: TeamFavouriteLocalDataSource
) : TeamFavouriteRepository {

    override fun markTeamAsFavourite(teamId: Int): Completable {
        return teamFavouriteLocalDataSource.markTeamAsFavourite(teamId)
    }

    override fun markTeamAsUnFavourite(teamId: Int): Completable {
        return teamFavouriteLocalDataSource.markTeamAsUnFavourite(teamId)
    }
}