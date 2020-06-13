package com.example.premierleagueapp.features.teamdetails.domain.repository

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.teamdetails.data.model.TeamDetailsResponse
import io.reactivex.Completable
import io.reactivex.Single

interface TeamDetailsRepository {
    fun getTeamDetails(teamId: Int): Single<TeamDetailsResponse>
    fun updateStoredTeamDetails(teamDetails: TeamDetailsEntity): Completable
    fun getTeamDetailsByIdFromDB(teamId: Int): Single<TeamDetailsEntity>
}