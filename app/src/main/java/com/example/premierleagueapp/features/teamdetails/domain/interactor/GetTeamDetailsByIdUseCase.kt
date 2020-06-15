package com.example.premierleagueapp.features.teamdetails.domain.interactor

import com.example.premierleagueapp.features.teamdetails.domain.repository.TeamDetailsRepository
import javax.inject.Inject

class GetTeamDetailsByIdUseCase @Inject constructor(
    private val teamDetailsRepository: TeamDetailsRepository
) {
    fun execute(teamId: Int) =
        teamDetailsRepository.getTeamDetailsByIdFromDB(teamId = teamId)
}