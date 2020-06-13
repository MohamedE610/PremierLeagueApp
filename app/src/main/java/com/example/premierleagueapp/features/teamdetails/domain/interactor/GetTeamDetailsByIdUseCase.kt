package com.example.premierleagueapp.features.teamdetails.domain.interactor

import com.example.premierleagueapp.features.teamdetails.data.mapper.map
import com.example.premierleagueapp.features.teamdetails.domain.repository.TeamDetailsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTeamDetailsByIdUseCase @Inject constructor(
    private val teamDetailsRepository: TeamDetailsRepository
) {

    // get team details from api, then update stored team details in database and in case of any error like no internet
    // i will get stored team details from database
    fun execute(teamId: Int) =
        teamDetailsRepository.getTeamDetails(teamId = teamId).flatMap {
            Single.fromCallable {
                val teamDetails = it.map()
                teamDetailsRepository.updateStoredTeamDetails(teamDetails)
                teamDetails
            }
        }.onErrorResumeNext {
            teamDetailsRepository.getTeamDetailsByIdFromDB(teamId = teamId)
        }
}