package com.example.premierleagueapp.features.teamdetails.data.source.local

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import io.reactivex.Completable
import io.reactivex.Single

interface TeamDetailsLocalDataSource {
    fun updateTeamDetails(teamDetails: TeamDetailsEntity): Completable
    fun getTeamDetailsByIdFromDB(teamId: Int): Single<TeamDetailsEntity>
}