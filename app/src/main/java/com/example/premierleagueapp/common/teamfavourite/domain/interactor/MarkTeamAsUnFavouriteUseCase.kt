package com.example.premierleagueapp.common.teamfavourite.domain.interactor

import com.example.premierleagueapp.common.teamfavourite.domain.repository.TeamFavouriteRepository
import javax.inject.Inject

class MarkTeamAsUnFavouriteUseCase @Inject constructor(private val teamFavouriteRepository: TeamFavouriteRepository) {

    fun execute(teamId: Int) = teamFavouriteRepository.markTeamAsUnFavourite(teamId)

}
