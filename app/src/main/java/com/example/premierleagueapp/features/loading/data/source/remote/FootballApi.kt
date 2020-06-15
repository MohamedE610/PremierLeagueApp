package com.example.premierleagueapp.features.loading.data.source.remote

import com.example.premierleagueapp.features.loading.data.model.TeamDetailsResponse
import com.example.premierleagueapp.features.loading.data.model.TeamsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface FootballApi {

    @GET("v2/competitions/{competitionId}/teams")
    fun getPremierLeagueTeams(
        @Path("competitionId") competitionId: Int = PREMIER_LEAGUE_ID
    ): Single<TeamsResponse>

    @GET("v2/teams/{teamId}")
    fun getTeamDetails(
        @Path("teamId") teamId: Int
    ): Single<TeamDetailsResponse>


}

private const val PREMIER_LEAGUE_ID = 2021