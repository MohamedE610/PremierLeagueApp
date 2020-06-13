package com.example.premierleagueapp.features.teamdetails.data.repository

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.teamdetails.data.model.TeamDetailsResponse
import com.example.premierleagueapp.features.teamdetails.data.source.local.TeamDetailsLocalDataSource
import com.example.premierleagueapp.features.teamdetails.data.source.remote.TeamDetailsRemoteDataSource
import com.example.premierleagueapp.features.teamdetails.domain.repository.TeamDetailsRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TeamDetailsRepositoryImpl @Inject constructor(
    private val teamDetailsLocalDataSource: TeamDetailsLocalDataSource,
    private val teamDetailsRemoteDataSource: TeamDetailsRemoteDataSource
) : TeamDetailsRepository {
    override fun getTeamDetails(teamId: Int): Single<TeamDetailsResponse> {
        return teamDetailsRemoteDataSource.getTeamDetails(teamId = teamId)
    }

    override fun updateStoredTeamDetails(teamDetails: TeamDetailsEntity): Completable {
        return teamDetailsLocalDataSource.updateTeamDetails(teamDetails = teamDetails)
    }

    override fun getTeamDetailsByIdFromDB(teamId: Int): Single<TeamDetailsEntity> {
        return teamDetailsLocalDataSource.getTeamDetailsByIdFromDB(teamId = teamId)
    }
}