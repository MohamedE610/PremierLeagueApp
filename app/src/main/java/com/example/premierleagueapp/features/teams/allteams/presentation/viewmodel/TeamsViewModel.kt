package com.example.premierleagueapp.features.teams.allteams.presentation.viewmodel

import android.util.Log
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
import com.example.premierleagueapp.core.presentation.modelwraper.ObservableResource
import com.example.premierleagueapp.core.presentation.viewmodel.BaseViewModel
import com.example.premierleagueapp.common.teamfavourite.domain.interactor.MarkTeamAsFavouriteUseCase
import com.example.premierleagueapp.common.teamfavourite.domain.interactor.MarkTeamAsUnFavouriteUseCase
import com.example.premierleagueapp.core.presentation.di.qualifier.IoScheduler
import com.example.premierleagueapp.core.presentation.di.qualifier.MainScheduler
import com.example.premierleagueapp.features.teams.allteams.domain.interactor.GetTeamsFromDBUseCase
import com.example.premierleagueapp.features.teams.allteams.presentation.mapper.map
import com.example.premierleagueapp.features.teams.allteams.presentation.model.TeamUI
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TeamsViewModel @Inject constructor(
    private val getTeamsFromDBUseCase: GetTeamsFromDBUseCase,
    @IoScheduler private val ioScheduler: Scheduler,
    @MainScheduler private val mainScheduler: Scheduler
) : BaseViewModel() {

    private var offsetDB = 0

    val moreTeamsObservableResource = ObservableResource<List<TeamUI>>()
    val teamsObservableResource = ObservableResource<List<TeamUI>>()

    fun loadFirstPage() {
        loadTeamsFromDB(teamsObservableResource)
    }

    fun loadMoreTeamsFromDBM() {
        loadTeamsFromDB(moreTeamsObservableResource)
    }

    private fun loadTeamsFromDB(observableResource: ObservableResource<List<TeamUI>>) {
        val disposable = getTeamsFromDBUseCase.execute(offset = offsetDB, limit = TEAMS_PAGE_SIZE)
            .delay(
                2,
                TimeUnit.SECONDS
            )/*just add delay 2 seconds to show loading for testing purpose*/
            .observeOn(mainScheduler)
            .subscribeOn(ioScheduler)
            .doOnSubscribe { observableResource.loading.postValue(true) }
            .doFinally { observableResource.loading.postValue(false) }
            .subscribe({
                it?.let { teams ->
                    observableResource.value = teams.map { team -> team.map() }
                    offsetDB += teams.size
                }
            }, {
                if (it is PremierLeagueException)
                    observableResource.error.value = it
                else
                    observableResource.error.value =
                        PremierLeagueException(PremierLeagueException.Kind.UNEXPECTED, it)
            })

        addDisposable(disposable)
    }

    companion object {
        private const val TEAMS_PAGE_SIZE = 6
    }
}
