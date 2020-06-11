package com.example.premierleagueapp.features.teams.data.source.local

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import io.reactivex.Completable
import io.reactivex.Single

interface TeamsLocalDataSource {
    fun getTeamsFromDB(offset: Int, limit: Int): Single<List<TeamDetailsEntity>>
    fun markTeamAsFavourite(teamId: Int):Completable
    fun markTeamAsUnFavourite(teamId: Int):Completable
}