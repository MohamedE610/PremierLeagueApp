package com.example.premierleagueapp.features.teamdetails.data.source.remote

import com.example.premierleagueapp.features.teamdetails.data.model.TeamDetailsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamDetailsApi {

    @GET("v2/teams/{teamId}")
    fun getTeamDetails(
        @Path("teamId") teamId: Int
    ): Single<TeamDetailsResponse>

}