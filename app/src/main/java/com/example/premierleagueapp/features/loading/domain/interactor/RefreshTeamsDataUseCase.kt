package com.example.premierleagueapp.features.loading.domain.interactor

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.loading.domain.repository.LoadingRepository
import javax.inject.Inject

class RefreshTeamsDataUseCase @Inject constructor(private val loadingRepository: LoadingRepository) {

    fun execute(teams: List<TeamDetailsEntity>) = loadingRepository.refreshTeamsData(teams)

}