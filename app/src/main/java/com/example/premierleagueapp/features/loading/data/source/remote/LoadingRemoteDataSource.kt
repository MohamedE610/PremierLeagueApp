package com.example.premierleagueapp.features.loading.data.source.remote

import com.example.premierleagueapp.features.loading.data.model.TeamDetailsResponse
import com.example.premierleagueapp.features.loading.data.model.TeamsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface LoadingRemoteDataSource {

    fun getPremierLeagueTeams(): Single<TeamsResponse>
    fun getTeamDetails(teamId: Int): Single<TeamDetailsResponse>

}