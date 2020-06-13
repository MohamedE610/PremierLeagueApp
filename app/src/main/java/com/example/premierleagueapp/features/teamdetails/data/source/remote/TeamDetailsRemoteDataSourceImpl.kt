package com.example.premierleagueapp.features.teamdetails.data.source.remote

import com.example.premierleagueapp.features.teamdetails.data.model.TeamDetailsResponse
import io.reactivex.Single
import javax.inject.Inject

class TeamDetailsRemoteDataSourceImpl @Inject constructor(private val teamDetailsApi: TeamDetailsApi) :
    TeamDetailsRemoteDataSource {

    override fun getTeamDetails(teamId: Int): Single<TeamDetailsResponse> {
        return teamDetailsApi.getTeamDetails(teamId = teamId)
    }
}