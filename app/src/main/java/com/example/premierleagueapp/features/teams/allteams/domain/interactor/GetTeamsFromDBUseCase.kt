package com.example.premierleagueapp.features.teams.allteams.domain.interactor

import com.example.premierleagueapp.features.teams.allteams.domain.model.TeamModel
import com.example.premierleagueapp.features.teams.allteams.domain.repository.TeamsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTeamsFromDBUseCase @Inject constructor(private val teamsRepository: TeamsRepository) {

    fun execute(offset: Int, limit: Int): Single<List<TeamModel>> {
        return teamsRepository.getTeamsFromDB(offset = offset, limit = limit)
    }

}
