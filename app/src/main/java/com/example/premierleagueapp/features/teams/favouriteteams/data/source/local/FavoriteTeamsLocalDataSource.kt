package com.example.premierleagueapp.features.teams.favouriteteams.data.source.local

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface FavoriteTeamsLocalDataSource {
    fun getFavouriteTeams(): Flowable<List<TeamDetailsEntity>>
}