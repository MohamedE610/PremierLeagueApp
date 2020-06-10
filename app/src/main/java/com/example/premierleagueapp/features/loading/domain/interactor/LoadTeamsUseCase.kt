package com.example.premierleagueapp.features.loading.domain.interactor

import com.example.premierleagueapp.features.loading.data.mapper.map
import io.reactivex.Completable
import javax.inject.Inject

class LoadTeamsUseCase @Inject constructor(
    private val getTeamsUseCase: GetTeamsUseCase,
    private val refreshTeamsDataUseCase: RefreshTeamsDataUseCase
) {

    fun execute(): Completable = getTeamsUseCase.execute().flatMapCompletable {
        refreshTeamsDataUseCase.execute(it.teams.map { team -> team.map() })
    }
}