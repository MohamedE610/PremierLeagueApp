package com.example.premierleagueapp.features.loading.domain.interactor

import com.example.premierleagueapp.features.loading.domain.repository.LoadingRepository
import javax.inject.Inject

class GetTeamsUseCase @Inject constructor(private val loadingRepository: LoadingRepository) {
    fun execute() = loadingRepository.getTeams()
}