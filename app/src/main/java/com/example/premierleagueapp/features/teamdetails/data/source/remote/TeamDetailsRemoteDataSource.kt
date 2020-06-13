package com.example.premierleagueapp.features.teamdetails.data.source.remote

import com.example.premierleagueapp.features.teamdetails.data.model.TeamDetailsResponse
import io.reactivex.Single

interface TeamDetailsRemoteDataSource {
    fun getTeamDetails(teamId: Int): Single<TeamDetailsResponse>
}