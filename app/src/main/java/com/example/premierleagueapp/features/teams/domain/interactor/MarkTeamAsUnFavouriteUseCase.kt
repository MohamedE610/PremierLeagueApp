package com.example.premierleagueapp.features.teams.domain.interactor

import com.example.premierleagueapp.features.teams.domain.repository.TeamsRepository
import javax.inject.Inject

class MarkTeamAsUnFavouriteUseCase @Inject constructor(private val teamsRepository: TeamsRepository) {

    fun execute(teamId: Int) = teamsRepository.markTeamAsUnFavourite(teamId)

}
