package com.example.premierleagueapp.features.loading.domain.interactor


import com.example.premierleagueapp.features.loading.data.mapper.map
import io.reactivex.Observable
import javax.inject.Inject

class LoadTeamsUseCase @Inject constructor(
    private val getTeamsUseCase: GetTeamsUseCase,
    private val getTeamDetailsUseCase: GetTeamDetailsUseCase,
    private val refreshTeamsDataUseCase: RefreshTeamsDataUseCase
) {

    fun execute() = getTeamsUseCase.execute().toObservable().flatMap {
        Observable.just(it.teams)
    }.flatMapIterable { teams -> teams }.flatMap {
        getTeamDetailsUseCase.execute(it.id).toObservable()
    }.toList().flatMapCompletable {
        refreshTeamsDataUseCase.execute(it.map { team -> team.map() })
    }
}