package com.example.premierleagueapp.features.teamdetails.domain.repository

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import io.reactivex.Single

interface TeamDetailsRepository {
    fun getTeamDetailsByIdFromDB(teamId: Int): Single<TeamDetailsEntity>
}