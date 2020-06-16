package com.example.premierleagueapp.features.teamdetails.presentation.viewmodel

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
import com.example.premierleagueapp.core.presentation.di.qualifier.IoScheduler
import com.example.premierleagueapp.core.presentation.di.qualifier.MainScheduler
import com.example.premierleagueapp.core.presentation.modelwraper.ObservableResource
import com.example.premierleagueapp.core.presentation.viewmodel.BaseViewModel
import com.example.premierleagueapp.features.teamdetails.domain.interactor.GetTeamDetailsByIdUseCase
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TeamDetailsViewModel @Inject constructor(
    private val getTeamDetailsByIdUseCase: GetTeamDetailsByIdUseCase,
    @IoScheduler private val ioScheduler: Scheduler,
    @MainScheduler private val mainScheduler: Scheduler
) : BaseViewModel() {

    val teamDetailsObservableResource = ObservableResource<TeamDetailsEntity>()

    fun getTeamDetails(teamId: Int) {
        val disposable = getTeamDetailsByIdUseCase.execute(teamId)
            .observeOn(mainScheduler)
            .subscribeOn(ioScheduler)
            .doOnSubscribe { teamDetailsObservableResource.loading.postValue(true) }
            .doFinally { teamDetailsObservableResource.loading.postValue(false) }
            .subscribe({
                teamDetailsObservableResource.value = it
            }, {
                if (it is PremierLeagueException)
                    teamDetailsObservableResource.error.value = it
                else
                    teamDetailsObservableResource.error.value =
                        PremierLeagueException(PremierLeagueException.Kind.UNEXPECTED, it)
            })

        addDisposable(disposable)
    }
}