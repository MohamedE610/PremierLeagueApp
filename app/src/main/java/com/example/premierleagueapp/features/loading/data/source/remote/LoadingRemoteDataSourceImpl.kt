package com.example.premierleagueapp.features.loading.data.source.remote

import com.example.premierleagueapp.features.teamdetails.data.model.TeamDetailsResponse
import com.example.premierleagueapp.features.loading.data.model.TeamsResponse
import io.reactivex.Single
import javax.inject.Inject

class LoadingRemoteDataSourceImpl @Inject constructor(private val footballApi: FootballApi) :
    LoadingRemoteDataSource {
    override fun getPremierLeagueTeams(): Single<TeamsResponse> {
        return footballApi.getPremierLeagueTeams()
    }

    override fun getTeamDetails(teamId: Int): Single<TeamDetailsResponse> {
        return footballApi.getTeamDetails(teamId)
    }
}