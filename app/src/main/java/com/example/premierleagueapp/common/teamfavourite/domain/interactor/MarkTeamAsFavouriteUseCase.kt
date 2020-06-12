package com.example.premierleagueapp.common.teamfavourite.domain.interactor

import com.example.premierleagueapp.common.teamfavourite.domain.repository.TeamFavouriteRepository
import javax.inject.Inject

class MarkTeamAsFavouriteUseCase @Inject constructor(private val teamFavouriteRepository: TeamFavouriteRepository) {

    fun execute(teamId: Int) = teamFavouriteRepository.markTeamAsFavourite(teamId)

}
