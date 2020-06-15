package com.example.premierleagueapp.features.teamdetails.data.repository

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.teamdetails.data.source.local.TeamDetailsLocalDataSource
import com.example.premierleagueapp.features.teamdetails.domain.repository.TeamDetailsRepository
import io.reactivex.Single
import javax.inject.Inject

class TeamDetailsRepositoryImpl @Inject constructor(
    private val teamDetailsLocalDataSource: TeamDetailsLocalDataSource
) : TeamDetailsRepository {

    override fun getTeamDetailsByIdFromDB(teamId: Int): Single<TeamDetailsEntity> {
        return teamDetailsLocalDataSource.getTeamDetailsByIdFromDB(teamId = teamId)
    }
}