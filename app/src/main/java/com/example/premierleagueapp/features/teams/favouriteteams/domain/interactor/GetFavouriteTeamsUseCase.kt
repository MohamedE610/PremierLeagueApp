package com.example.premierleagueapp.features.teams.favouriteteams.domain.interactor

import com.example.premierleagueapp.features.teams.favouriteteams.domain.repository.FavouriteTeamsRepository
import javax.inject.Inject

class GetFavouriteTeamsUseCase @Inject constructor(
    private val favouriteTeamsRepository: FavouriteTeamsRepository
) {
    fun execute() = favouriteTeamsRepository.getFavouriteTeams()
}