package com.example.premierleagueapp.features.loading.data.repository

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.teamdetails.data.model.TeamDetailsResponse
import com.example.premierleagueapp.features.loading.data.model.TeamsResponse
import com.example.premierleagueapp.features.loading.data.source.local.LoadingLocalDataSource
import com.example.premierleagueapp.features.loading.data.source.remote.LoadingRemoteDataSource
import com.example.premierleagueapp.features.loading.domain.repository.LoadingRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoadingRepositoryImpl @Inject constructor(
    private val loadingLocalDataSource: LoadingLocalDataSource,
    private val loadingRemoteDataSource: LoadingRemoteDataSource
) : LoadingRepository {

    override fun getTeams(): Single<TeamsResponse> {
        return loadingRemoteDataSource.getPremierLeagueTeams()
    }

    override fun getTeamDetailsById(teamId: Int): Single<TeamDetailsResponse> {
        return loadingRemoteDataSource.getTeamDetails(teamId)
    }

    override fun refreshTeamsData(teams: List<TeamDetailsEntity>): Completable {
        return loadingLocalDataSource.refreshTeamsData(teams)
    }

}