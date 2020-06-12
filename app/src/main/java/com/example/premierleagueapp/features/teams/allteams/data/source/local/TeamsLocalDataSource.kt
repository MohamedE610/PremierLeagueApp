package com.example.premierleagueapp.features.teams.allteams.data.source.local

import com.example.premierleagueapp.features.teams.allteams.domain.model.TeamModel
import io.reactivex.Single

interface TeamsLocalDataSource {
    fun getTeamsFromDB(offset: Int, limit: Int): Single<List<TeamModel>>
}