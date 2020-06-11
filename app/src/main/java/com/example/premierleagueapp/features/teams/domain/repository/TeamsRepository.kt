package com.example.premierleagueapp.features.teams.domain.repository

import com.example.premierleagueapp.features.teams.domain.model.TeamModel
import io.reactivex.Completable
import io.reactivex.Single

interface TeamsRepository {
    fun getTeamsFromDB(offset: Int, limit: Int): Single<List<TeamModel>>
    fun markTeamAsFavourite(teamId: Int):Completable
    fun markTeamAsUnFavourite(teamId: Int):Completable
}