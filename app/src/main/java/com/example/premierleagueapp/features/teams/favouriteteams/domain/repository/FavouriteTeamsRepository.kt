package com.example.premierleagueapp.features.teams.favouriteteams.domain.repository

import com.example.premierleagueapp.features.teams.allteams.domain.model.TeamModel
import io.reactivex.Flowable
import io.reactivex.Single

interface FavouriteTeamsRepository {
    fun getFavouriteTeams(): Flowable<List<TeamModel>>
}