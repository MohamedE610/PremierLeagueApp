package com.example.premierleagueapp.features.teams.allteams.domain.repository


import com.example.premierleagueapp.features.teams.allteams.domain.model.TeamModel
import io.reactivex.Single

interface TeamsRepository {
    fun getTeamsFromDB(offset: Int, limit: Int): Single<List<TeamModel>>
}