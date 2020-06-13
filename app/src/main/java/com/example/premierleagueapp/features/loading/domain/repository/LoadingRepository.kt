package com.example.premierleagueapp.features.loading.domain.repository

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.teamdetails.data.model.TeamDetailsResponse
import com.example.premierleagueapp.features.loading.data.model.TeamsResponse
import io.reactivex.Completable
import io.reactivex.Single

interface LoadingRepository {

    fun getTeams(): Single<TeamsResponse>
    fun getTeamDetailsById(teamId: Int): Single<TeamDetailsResponse>
    fun refreshTeamsData(teams: List<TeamDetailsEntity>): Completable

}